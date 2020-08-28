package io.github.golok56.githubuser.domain.usecase

class NoInputException(className: String) :
    IllegalArgumentException("Jangan lupa untuk memasukkan parameter di kelas $className")