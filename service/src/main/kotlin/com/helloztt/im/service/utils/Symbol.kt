package com.helloztt.im.service.utils

/**
 * TODO
 *
 * @author helloztt
 * @since 2018-05-15 23:40
 */
enum class Symbol(var value:String) {
    /**
     * 减号-
     */
    MINUS("-"),
    /**
     * 英文逗号,
     */
    COMMA(","),
    /**
     * 下划线_
     */
    UNDERLINE("_"),
    /**
     * 百分号%
     */
    PERCENT("%"),
    /**
     * 换行符\r\n
     */
    ENTER("\r\n"),
    /**
     * 单竖杠|
     */
    SINGLE_VERTICAL_BAR("|"),
    /**
     * 英文冒号:
     */
    COLON_EN(":"),
    /**
     * 中文逗号，
     */
    COMMA_CN("，"),
    /**
     * 空格
     */
    SPACE(" "),
    /**
     * 左方括号[
     */
    LEFT_SQUARE_BRACKET("["),
    /**
     * 右方括号]
     */
    RIGHT_SQUARE_BRACKET("]"),
    /**
     * 左大括号 {
     */
    LEFT_BRACE("{"),
    /**
     * 右大括号}
     */
    RIGHT_BRACE("}"),
    /**
     * 中文顿号、
     */
    PUNCTUATION_MARK_CN("、"),
    /**
     * AND &
     */
    AND("&"),
    /**
     * =
     */
    EQUAL("="),
    /**
     * #
     */
    WELL_NUMBER("#"),
    /**
     * 中文左括号（
     */
    LEFT_PARENTHESE_CN("（"),
    /**
     * 中文右括号）
     */
    RIGHT_PARENTHESE_CN("）"),
    /**
     * @
     */
    EMAIL("@"),
    /**
     * ? 问号
     */
    QUESTION_MARK("?"),
    /**
     * 左斜杠  /
     */
    LEFT_SLASH("/"),
    /**
     * 点 .
     */
    DOT("."),
    /**
     * 星号 *
     */
    ASTERISK("*"),
    /**
     * 分号中文 ；
     */
    SEMICOLON_CN("；");
}