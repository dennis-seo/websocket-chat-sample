package com.jeffrey.melonchatserver


/*
* Copyright (C) 2022 Kakao corp. All rights reserved.
*
* Created by Jeffrey.bbongs on 2022/06/14
*
*/
interface ChatServerListener {
    fun onMessage(message: String)
}