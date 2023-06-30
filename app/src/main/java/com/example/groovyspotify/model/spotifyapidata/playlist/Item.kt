package com.example.groovyspotify.model.spotifyapidata.playlist

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    val added_at: String= "",
    val added_by: AddedBy = AddedBy(),
    val is_local: Boolean = false,
    val primary_color: String= "",
    val track: Track = Track(),
    val video_thumbnail: VideoThumbnail = VideoThumbnail()
) : Parcelable