package com.map.map.service.jwt

import com.map.map.domain.entity.User
import com.map.map.domain.repository.UserRepo
import com.map.map.domain.response.auth.UserTokenRes
import com.map.map.enum.JwtType
import com.map.map.exception.CustomHttpException
import io.jsonwebtoken.*
import io.jsonwebtoken.security.SignatureException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import java.security.Key
import java.util.*
import javax.crypto.spec.SecretKeySpec
import javax.transaction.Transactional

@Service
class JwtServiceImpl : JwtService {

    @Autowired
    lateinit var userRepo: UserRepo

    @Value("\${jwt.auth.access}")
    private val secretAccessKey: String? = null

    @Value("\${jwt.auth.refresh}")
    private val secretRefreshKey: String? = null

    val signatureAlgorithm: SignatureAlgorithm = SignatureAlgorithm.HS256

    private val TIME: Long = 1000 * 60 * 60
    private val TWOWEEKS: Long = TIME * 24 * 7 * 2

    /**
     * 토큰 생성
     */
    override fun createToken(id: String, authType: JwtType): String {
        var expiredAt = Date()

        val secretKey: String? = when (authType) {
            JwtType.ACCESS -> {
                expiredAt = Date(expiredAt.time + TIME)
                secretAccessKey
            }
            JwtType.REFRESH -> {
                expiredAt = Date(expiredAt.time + TWOWEEKS)
                secretRefreshKey
            }
        }

        val signInKey = SecretKeySpec(secretKey!!.toByteArray(), signatureAlgorithm.jcaName)

        val headerMap: MutableMap<String, Any> = HashMap()

        headerMap["typ"] = "JWT"
        headerMap["alg"] = "HS256"

        val map: MutableMap<String, Any> = HashMap()

        map["id"] = id
        map["authType"] = authType

        return Jwts.builder()
            .setHeaderParams(headerMap)
            .setClaims(map)
            .setExpiration(expiredAt)
            .signWith(signInKey)
            .compact()
    }

    /**
     * 토큰 유효 검증
     */
    @Transactional
    override fun validateToken(token: String?): String? {
        try {
            val signInKey: Key = SecretKeySpec(secretAccessKey!!.toByteArray(), signatureAlgorithm.jcaName)
            val claims: Claims = Jwts.parserBuilder()
                .setSigningKey(signInKey)
                .build()
                .parseClaimsJws(token)
                .body

            if (claims["authType"].toString() != "ACCESS") {
                throw CustomHttpException(HttpStatus.UNAUTHORIZED, "토큰 타입 아님")
            }

            return userRepo.findById(claims["id"].toString())?.id
                ?: throw CustomHttpException(HttpStatus.NOT_FOUND, "유저 없음")
        } catch (e: ExpiredJwtException) {
            throw CustomHttpException(HttpStatus.GONE, "토큰 만료")
        } catch (e: SignatureException) {
            throw CustomHttpException(HttpStatus.UNAUTHORIZED, "토큰 위조")
        } catch (e: MalformedJwtException) {
            throw CustomHttpException(HttpStatus.UNAUTHORIZED, "토큰 위조")
        } catch (e: IllegalArgumentException) {
            throw CustomHttpException(HttpStatus.BAD_REQUEST, "토큰 없음")
        } catch (e: HttpClientErrorException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            throw CustomHttpException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류")
        }
    }

    /**
     * 토큰 갱신
     */
    override fun refreshToken(refreshToken: String?, accessToken: String?): UserTokenRes {
        try {
            tokenNotNull(refreshToken)

            val signInKey = SecretKeySpec(secretRefreshKey!!.toByteArray(), signatureAlgorithm.jcaName)
            val claims = Jwts.parserBuilder()
                .setSigningKey(signInKey)
                .build()
                .parseClaimsJws(refreshToken)
                .body

            if (claims["authType"].toString() != "REFRESH") {
                throw CustomHttpException(HttpStatus.UNAUTHORIZED, "토큰 타입 아님")
            }

            val user: User = userRepo.findById(claims["id"].toString())
                ?: throw CustomHttpException(HttpStatus.NOT_FOUND, "유저 없음.")

            val accessTokenRes = this.createToken(user.id!!, JwtType.ACCESS)
            val refreshTokenRes: String
            val accessTokenExp = this.getAccessTokenExp(accessToken)
            refreshTokenRes = if (claims["exp"].toString().toLong() - accessTokenExp < TWOWEEKS / 2) {
                this.createToken(user.id!!, JwtType.REFRESH)
            } else {
                refreshToken!!
            }

            return UserTokenRes(accessTokenRes, refreshTokenRes)
        } catch (e: ExpiredJwtException) {
            throw CustomHttpException(HttpStatus.GONE, "토큰 만료")
        } catch (e: SignatureException) {
            throw CustomHttpException(HttpStatus.UNAUTHORIZED, "토큰 위조")
        } catch (e: MalformedJwtException) {
            throw CustomHttpException(HttpStatus.UNAUTHORIZED, "토큰 위조")
        } catch (e: IllegalArgumentException) {
            throw CustomHttpException(HttpStatus.BAD_REQUEST, "토큰 없음")
        } catch (e: CustomHttpException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            throw CustomHttpException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류")
        }
    }

    /**
     * 토큰 null 확인
     */
    private fun tokenNotNull(token: String?) {
        if (token == null || token.trim().isEmpty()) {
            throw CustomHttpException(HttpStatus.BAD_REQUEST, "검증 오류")
        }
    }

    /**
     * accessToken 만료시간 얻기
     */
    private fun getAccessTokenExp(accessToken: String?): Long {
        try {
            tokenNotNull(accessToken)

            val signInKey = SecretKeySpec(secretRefreshKey!!.toByteArray(), signatureAlgorithm.jcaName)
            val claims = Jwts.parserBuilder()
                .setSigningKey(signInKey)
                .build()
                .parseClaimsJws(accessToken)
                .body

            return claims["exp"].toString().toLong()
        } catch (e: Exception) {
            throw CustomHttpException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러")
        }
    }
}