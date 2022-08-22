package com.ferpa.moviechallenge.data.utils

import androidx.room.TypeConverter
import com.ferpa.moviechallenge.model.Genre
import com.ferpa.moviechallenge.model.ProductionCompany
import com.ferpa.moviechallenge.model.ProductionCountry
import com.ferpa.moviechallenge.model.SpokenLanguage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

class Converters {

    @TypeConverter
    fun stringToListGenres(data: String?): List<Genre?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object :
            TypeToken<List<Genre?>?>() {}.type
        return Gson().fromJson<List<Genre?>>(data, listType)
    }

    @TypeConverter
    fun listGenreToString(someObjects: List<Genre?>?): String? {
        return Gson().toJson(someObjects)
    }

    @TypeConverter
    fun stringToListProductionCompanies(data: String?): List<ProductionCompany?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object :
            TypeToken<List<ProductionCompany?>?>() {}.type
        return Gson().fromJson<List<ProductionCompany?>>(data, listType)
    }

    @TypeConverter
    fun listProductionCompaniesToString(someObjects: List<ProductionCompany?>?): String? {
        return Gson().toJson(someObjects)
    }

    @TypeConverter
    fun stringToListProductionCountries(data: String?): List<ProductionCountry?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object :
            TypeToken<List<ProductionCountry?>?>() {}.type
        return Gson().fromJson<List<ProductionCountry?>>(data, listType)
    }

    @TypeConverter
    fun listProductionCountriesToString(someObjects: List<ProductionCountry?>?): String? {
        return Gson().toJson(someObjects)
    }

    @TypeConverter
    fun stringToListSpokenLanguages(data: String?): List<SpokenLanguage?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object :
            TypeToken<List<SpokenLanguage?>?>() {}.type
        return Gson().fromJson<List<SpokenLanguage?>>(data, listType)
    }

    @TypeConverter
    fun listSpokenLanguagesToString(someObjects: List<SpokenLanguage?>?): String? {
        return Gson().toJson(someObjects)
    }


}