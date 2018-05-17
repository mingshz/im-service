package com.helloztt.im.service.utils

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.springframework.util.StringUtils

/**
 *
 * @author helloztt
 * @since 2018-05-16 00:02
 */
object JsonUtils{
    val mapper = ObjectMapper()

    init {
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
    }

    /**
     * 对象转换成json格式
     *
     * @param obj
     * @return
     */
    fun writeValueToString(obj: Any): String {
        try {
            return mapper.writeValueAsString(obj)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    /**
     * 对象转换成 byte数组
     */
    fun writeValueToByte(obj: Any): ByteArray? {
        try {
            return mapper.writeValueAsBytes(obj)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    /**
     * json格式转换成对象
     *
     * @param json
     * @param clazz
     * @return
     */
    fun <T> readValue(json: String, clazz: Class<T>): T? {
        if (StringUtils.isEmpty(json)) {
            return null
        }
        try {
            return mapper.readValue(json, clazz)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}