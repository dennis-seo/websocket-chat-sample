package com.jeffrey.melonchatserver


/*
* Copyright (C) 2022 Kakao corp. All rights reserved.
*
* Created by Jeffrey.bbongs on 2022/06/17
*
* https://podotree.atlassian.net/wiki/spaces/platformdev2/pages/2889613472/CHAT+Melon+Message#SEND_MESSAGE
*/
sealed class ServerMessage

object InitMessage : ServerMessage()        // 사용자 채팅방 입장시 최초 전달되는 정보 메세지
object PingMessage : ServerMessage()        // Ping 메세지
object UserMessage : ServerMessage()        // 사용자 메세지
object UserFixedMessage : ServerMessage()   // 사용자 고정 메세지
object ArtistMessage : ServerMessage()      // 아트스트 메세지
object ArtistFixedMessage : ServerMessage() // 에러 발생시 메세지
object NoticeMessage : ServerMessage()      // 흘러가는 공지사항 메세지

object CustomMessage : ServerMessage()      // 흘러가는 공지사항 메세지

fun ServerMessage.getJSonMessage(message: String? = null): String {
    return when (this) {
        is InitMessage -> """
            {
              "id": 9900023671212122,
              "type": "INIT_MESSAGE",
              "roomId": 9900023675678122,
              "chatMeta": {
                "id": 9900034671212122,
                "name": "채팅방명",
                "startDt": "2022-04-25T14:00:00+09:00",
                "endDt": "2022-04-25T14:00:00+09:00",
                "systemGuide": "시스템안내문구",
                "usageGuide": "이용안내문구",
                "abuseGuide": "욕설안내문구",
                "spamGuide": "도배안내문구",
                "spamRule": "10-10",
                "userWelcomeGuide": "유저 환영문구",      
                "artistWelcomeGuide": "아티스트 환영문구",
                "maxUserCountPerRoom": 1000,
                "userChatFixedDuration": 30,
                "maxUserChatFixedCount": 5,
                "artistChatFixedDuration": 60,
                "maxArtistChatFixedCount": 5
              },
              "noticeMessages": [],
              "userFixedMessages": [],
              "artistFixedMessages": [],
              "createdDt": "2022-04-25T14:00:00+09:00"
            }
        """.trimIndent()

        is PingMessage -> """
            {
              "type": "PING",
              "message": "PING"
            }
        """.trimIndent()

        is UserMessage -> """
            {
              "id": 9900012121212122,
              "type": "USER_MESSAGE",
              "rawMessage": "너 내가 걸리면 죽여 버릴꺼야~ 개새끼야!",
              "message": "너 내가 걸리면 <blind>죽여</blind> 버릴꺼야~ <swear>개새끼</swear>야!",
              "sentTime": "2022-04-25T14:00:00+09:00",
              "includeBannedWord": true,
              "fromUser":{
                "id": "tester",
                "name": "홍길동",
                "profileImage": "https://image.com/profile.png",
                "gradle": "USER"
              },
              "createdDt": "2022-04-25T14:00:00+09:00"
            }
        """.trimIndent()

        is UserFixedMessage -> """
            {
              "id": 9900012121212122,
              "type": "USER_MESSAGE",
              "rawMessage": "너 내가 걸리면 죽여 버릴꺼야~ 개새끼야!",
              "message": "너 내가 걸리면 <blind>죽여</blind> 버릴꺼야~ <swear>개새끼</swear>야!",
              "sentTime": "2022-04-25T14:00:00+09:00",
              "includeBannedWord": true,
              "fromUser":{
                "id": "tester",
                "name": "홍길동",
                "profileImage": "https://image.com/profile.png",
                "gradle": "USER"
              },
              "createdDt": "2022-04-25T14:00:00+09:00"
            }
        """.trimIndent()

        is ArtistMessage -> """
            {
              "id": 9900012121212122,
              "type": "ARTIST_MESSAGE",
              "message": "여러분 모두 안녕하세요~",
              "sentTime": "2022-04-25T14:00:00+09:00",
              "fromUser":{
                "id": "tester",
                "name": "홍길동",
                "profileImage": "https://image.com/profile.png",
                "gradle": "ARTIST"
              },
              "createdDt": "2022-04-25T14:00:00+09:00"
            }
        """.trimIndent()

        is ArtistFixedMessage -> """
            {
              "id": 9900012121212122,
              "type": "ARTIST_FIXED_MESSAGE",
              "message": "여러분 모두 안녕하세요~",
              "sentTime": "2022-04-25T14:00:00+09:00",
              "fromUser":{
                "id": "tester",
                "name": "홍길동",
                "profileImage": "https://image.com/profile.png",
                "gradle": "ARTIST"
              },
              "createdDt": "2022-04-25T14:00:00+09:00"
            }
        """.trimIndent()

        is NoticeMessage -> """
            {
              "id": 9900012121212122,
              "type": "NOTICE_MESSAGE",
              "message": "공지사항은 이렇고 저렇습니다",
              "actionTitle": "타이틀",
              "actionUrl": "https://www.kakaoent.com",
              "createdDt": "2022-04-25T14:00:00+09:00"
            }
        """.trimIndent()

        is CustomMessage -> """
            {
              "id": 9900012121212122,
              "type": "USER_MESSAGE",
              "rawMessage": "너 내가 걸리면 죽여 버릴꺼야~ 개새끼야!",
              "message": ${message},
              "sentTime": "2022-04-25T14:00:00+09:00",
              "includeBannedWord": true,
              "fromUser":{
                "id": "tester",
                "name": "홍길동",
                "profileImage": "https://image.com/profile.png",
                "gradle": "USER"
              },
              "createdDt": "2022-04-25T14:00:00+09:00"
            }
        """.trimIndent()
    }
}

