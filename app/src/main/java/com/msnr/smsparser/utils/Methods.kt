package com.msnr.smsparser.utils

class Methods {
    companion object {
        fun extractUrls(text: String): List<String> {
            val regex = Regex("((?:https?|www)://[\\w\\.-]+)")
            val matches = regex.findAll(text)
            val urls = mutableListOf<String>()
            for (match in matches) {
                urls.add(match.value)
            }
            return urls
        }
    }
}