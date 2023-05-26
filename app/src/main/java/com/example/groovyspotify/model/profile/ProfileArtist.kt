package com.example.groovyspotify.model.profile

import android.provider.ContactsContract.Profile

data class ProfileArtist(
    val name: String,
    val language: MusicLanguage,
    val id: String,
)

val listOfTeluguArtists = listOf<ProfileArtist>(
    ProfileArtist("Mani Sharma",MusicLanguage("Telugu"), "3AMxH9QIPZbK8ND8C9j4Ss"),
    ProfileArtist("Devi Sri Prasad",MusicLanguage("Telugu"), "5sSzCxHtgL82pYDvx2QyEU"),
    ProfileArtist("Thaman S",MusicLanguage("Telugu"), "2FgHPfRprDaylrSRVf1UlN"),
    ProfileArtist("Anirudh Ravichander",MusicLanguage("Telugu"), "4zCH9qm4R2DADamUHMCa6O"),
    ProfileArtist("Sid Sriram",MusicLanguage("Telugu"),"7qjJw7ZM2ekDSahLXPjIlN"),
    ProfileArtist("M.M.Keeravani", MusicLanguage("Telugu"),"12l1SqSNsg2mI2IcXpPWjR"),
    ProfileArtist("Anup Rubens", MusicLanguage("Telugu"),"05ppw2jwDfW9zo4mj6HNXp"),
    ProfileArtist("Mickey J. Meyer",MusicLanguage("Telugu"),"0WiZi3Q419nMpAQEqfBCbk"),
    ProfileArtist("Sunitha", MusicLanguage("Telugu"),"74S8YRi4XQ5yf9ToSzuY80"),
    ProfileArtist("S.P. Charan", MusicLanguage("Telugu"),"1BIAyVQxDuYrMqZ8H1Faja"),
    ProfileArtist("S.P. Balasubrahmanyam", MusicLanguage("Telugu"),"2ae6PxICSOZHvjqiCcgon8"),
    ProfileArtist("Ilaiyaraaja",MusicLanguage("Telugu"),"3m49WVMU4zCkaVEKb8kFW7"),
    ProfileArtist("Harris Jayaraj", MusicLanguage("Telugu"),"29aw5YCdIw2FEXYyAJZI8l"),
    ProfileArtist("Yuvan Shankar Raja", MusicLanguage("Telugu"),"6AiX12wXdXFoGJ2vk8zBjy"),
    ProfileArtist("G.V. Prakash", MusicLanguage("Telugu"),"5VVN3xZw1i2qihfITZlvCZ"),
    ProfileArtist("A.R. Rahman",MusicLanguage("Telugu"),"1mYsTxnqsietFxj1OgoGbG"),
)
val listOfHindiArtists = listOf<ProfileArtist>(
    ProfileArtist("Pritam",MusicLanguage("Hindi"),"1wRPtKGflJrBx9BmLsSwlU"),
    ProfileArtist("Amit Trivedi", MusicLanguage("Hindi"),"7HCqGPJcQTyGJ2yqntbuyr"),
    ProfileArtist("Mithoon", MusicLanguage("Hindi"),"09UmIX92EUH9hAK4bxvHx6"),
    ProfileArtist("Shankar-Ehsaan-Loy", MusicLanguage("Hindi"),"0L5GV6LN8SWWUWIdBbTLTZ"),
    ProfileArtist("Vishal-Shekhar",MusicLanguage("Hindi"),"6Mv8GjQa7LKUGCAqa9qqdb"),
    ProfileArtist("Shreya Ghoshal",MusicLanguage("Hindi"),"0oOet2f43PA68X5RxKobEy"),
    ProfileArtist("Arijit Singh",MusicLanguage("Hindi"),"4YRxDV8wJFPHPTeXepOstw"),
    ProfileArtist("Javed Ali", MusicLanguage("Hindi"),"4W91bbPB2CTSsHwt7eqNl7"),
    ProfileArtist("Mohit Chauhan", MusicLanguage("Hindi"),"5GnnSrwNCGyfAU4zuIytiS"),
    ProfileArtist("Vishal Dadlani", MusicLanguage("Hindi"),"6CXEwIaXYfVJ84biCxqc9k"),
    ProfileArtist("Neha Kakkar", MusicLanguage("Hindi"),"5f4QpKfy7ptCHwTqspnSJI"),
    ProfileArtist("Badshah", MusicLanguage("Hindi"),"0y59o4v8uw5crbN9M3JiL1"),
    ProfileArtist("Sachet Tandon", MusicLanguage("Hindi"),"6WOdPJmexxFINcKMkP2jMG"),
    ProfileArtist("Dhvani Bhanushali", MusicLanguage("Hindi"),"1OPqAyxsQc8mcRmoNBAnVk"),
    ProfileArtist("Himesh Reshammiya", MusicLanguage("Hindi"),"0sSxphmGskGCKlwB9xa6WU"),
    ProfileArtist("Atif Aslam", MusicLanguage("Hindi"),"2oSONSC9zQ4UonDKnLqksx")
)
val listOfEnglishArtists = listOf<ProfileArtist>(
    ProfileArtist("Adan Ricky",MusicLanguage("English"),"4D55EuvmIWO8OQ1AlhrZU4"),
    ProfileArtist("Justin Bieber",MusicLanguage("English"),"1uNFoZAHBGtllmzznpCI3s"),
    ProfileArtist("Selena Gomez",MusicLanguage("English"),"0C8ZW7ezQVs4URX5aX7Kqx"),
    ProfileArtist("Camila Cabello",MusicLanguage("English"),"4nDoRrQiYLoBzwC5BhVJzF"),
    ProfileArtist("Ariana Grande",MusicLanguage("English"),"66CXWjxzNUsdJxJ2JdwvnR"),
    ProfileArtist("Dua Lipa",MusicLanguage("English"),"6M2wZ9GZgrQXHCFfjv46we"),
    ProfileArtist("The Weeknd",MusicLanguage("English"),"1Xyo4u8uXC1ZmMpatF05PJ"),
    ProfileArtist("Kendrick Lamar",MusicLanguage("English"),"2YZyLoL8N0Wb9xBt1NhZWg"),
    ProfileArtist("Chris Brown",MusicLanguage("English"),"7bXgB6jMjp9ATFy66eO08Z"),
    ProfileArtist("Taylor Swift", MusicLanguage("English"),"06HL4z0CvFAxyc27GXpf02"),
    ProfileArtist("Maroon 5", MusicLanguage("English"),"04gDigrS5kc9YWfZHwBETP"),
    ProfileArtist("Shawn Mendes", MusicLanguage("English"),"7n2wHs1TKAczGzO7Dd2rGr"),
    ProfileArtist("Jason Derulo", MusicLanguage("English"),"07YZf4WDAMNwqr4jfgOZ8y"),
    ProfileArtist("Charlie Puth", MusicLanguage("English"),"6VuMaDnrHyPL1p4EHjYLi7"),
    ProfileArtist("Mabel", MusicLanguage("English"),"1MIVXf74SZHmTIp4V4paH4"),
    ProfileArtist("Ava Max", MusicLanguage("English"),"4npEfmQ6YuiwW1GpUmaq3F")
)