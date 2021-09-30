package com.map.map.domain.repository.paging

import com.map.map.domain.entity.Album
import org.springframework.data.repository.PagingAndSortingRepository

interface AlbumListRepo : PagingAndSortingRepository<Album, Long> {

}