package com.github.recraftedcivilizations.dataparser

import com.github.recraftedcivilizations.dataparser.blockparser.IParseBlocks
import com.github.recraftedcivilizations.dataparser.timeparser.IParseTimes

/**
 * @author DarkVanityOfLight
 */

/**
 * Store and get data about blocks that have
 * to be regenerated
 */
interface IParseData : IParseBlocks, IParseTimes