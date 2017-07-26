package com.s4win.whatwelove

import com.android.volley.Request
import com.s4win.whatwelove.spike.TargetType

/**
 * Created by dariopellegrini on 26/07/17.
 */

data class GetShows(val query: String): TVMazeTarget()
data class GetSingleShow(val query: String): TVMazeTarget()
data class GetPeople(val query: String): TVMazeTarget()
data class GetShowInformation(val showID: String, val embed: String): TVMazeTarget()
data class GetEdisodesByNumber(val showID: String, val season: Int, val number: Int): TVMazeTarget()

// Following actually don't exists
data class AddShow(val name: String, val token: String): TVMazeTarget()
data class UpdateShow(val showID: String, val name: String, val token: String): TVMazeTarget()
data class DeleteShow(val showID: String, val token: String): TVMazeTarget()

sealed class TVMazeTarget: TargetType {

    override val baseURL: String
        get() {
            return "https://api.tvmaze.com/"
        }

    override val path: String
        get() {
            when(this) {
                is GetShows -> return "search/shows"
                is GetSingleShow -> return "singlesearch/shows"
                is GetPeople -> return "search/people"
                is GetShowInformation -> return "shows/" + showID
                is GetEdisodesByNumber -> return "shows/" + showID
                is AddShow -> return "shows/"
                is UpdateShow -> return "shows/" + showID
                is DeleteShow -> return "shows/" + showID
            }
        }

    override val method: Int
        get() {
            when(this) {
                is GetShows -> return Request.Method.GET
                is GetSingleShow -> return Request.Method.GET
                is GetPeople -> return Request.Method.GET
                is GetShowInformation -> return Request.Method.GET
                is GetEdisodesByNumber -> return Request.Method.GET
                is AddShow -> return Request.Method.POST
                is UpdateShow -> return Request.Method.PATCH
                is DeleteShow -> return Request.Method.DELETE
            }
        }
    override val headers: Map<String, String>?
        get() {
            when(this) {
                is GetShows -> return mapOf("Content-Type" to "application/json")
                is GetSingleShow -> return mapOf("Content-Type" to "application/json")
                is GetPeople -> return mapOf("Content-Type" to "application/json")
                is GetShowInformation -> return mapOf("Content-Type" to "application/json")
                is GetEdisodesByNumber -> return mapOf("Content-Type" to "application/json")
                is AddShow -> return mapOf("Content-Type" to "application/json", "user_token" to token)
                is UpdateShow -> return mapOf("Content-Type" to "application/json", "user_token" to token)
                is DeleteShow -> return mapOf("Content-Type" to "application/json", "user_token" to token)
            }
        }
    override val parameters: Map<String, Any>?
        get() {
            when(this) {
                is GetShows -> return mapOf("q" to query)
                is GetSingleShow -> return mapOf("q" to query)
                is GetPeople -> return mapOf("q" to query)
                is GetShowInformation -> return mapOf("embed" to embed)
                is GetEdisodesByNumber -> return mapOf("season" to season, "number" to number)
                is AddShow -> return mapOf("name" to name)
                is UpdateShow -> return mapOf("name" to name)
                is DeleteShow -> return null
            }
        }
}