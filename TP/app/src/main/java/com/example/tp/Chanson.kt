package com.example.tp

data class Chanson (
    val id: String?,
    val title: String?,
    val album: String?,
    val artist: String?,
    val genre: String?,
    val source: String?,
    val image: String?,
    val trackNumber: Int?,
    val totalTrackCount: Int?,
    val duration: Int?,
    val site: String?
)
//    companion object{
//        fun builder() = Builder()
//    }
//
//    class Builder {
//        var cid: String? = null
//            private set
//        var title: String? = null
//            private set
//        var album: String? = null
//            private set
//        var artist: String? = null
//            private set
//        var genre: String? = null
//            private set
//        var source: String? = null
//            private set
//        var img: String? = null
//            private set
//        var trackNumber: Int? = null
//            private set
//        var totalTrackCount: Int? = null
//            private set
//        var duration: Int? = null
//            private set
//        var site: String? = null
//            private set
//
//        fun cid(cid: String): Builder {
//            this.cid = cid
//            return this
//        }
//
//        fun title(title: String): Builder{
//            this.title = title
//            return this
//        }
//
//        fun album(album: String): Builder{
//            this.album = album
//            return this
//        }
//
//        fun artist(artist: String): Builder{
//            this.artist = artist
//            return this
//        }
//
//        fun genre(genre: String): Builder{
//            this.genre = genre
//            return this
//        }
//
//        fun source(source: String): Builder{
//            this.source = source
//            return this
//        }
//
//        fun img(img: String): Builder{
//            this.img = img
//            return this
//        }
//
//        fun trackNumber(trackNumber: Int): Builder{
//            this.trackNumber = trackNumber
//            return this
//        }
//
//        fun totalTrackCount(totalTrackCount: Int): Builder{
//            this.totalTrackCount = totalTrackCount
//            return this
//        }
//
//        fun duration(duration: Int): Builder{
//            this.duration = duration
//            return this
//        }
//
//        fun site(site: String): Builder{
//            this.site = site
//            return this
//        }
//
//
//        fun build(): Chanson = Chanson(this)
//
//
//    }
