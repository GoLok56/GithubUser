package io.github.golok56.githubuser.data.repository.githubuser

class LimitReachedException : Exception("Batas request ke server github sudah habis, silakan tunggu sebentar lagi untuk melakukan request.")