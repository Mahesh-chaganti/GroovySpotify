package com.example.groovyspotify.model.profile

import android.os.Parcelable
import android.provider.ContactsContract.Profile
import kotlinx.parcelize.Parcelize


data class ProfileArtist(
    var name: String = "",
    var language: String = "",
    var id: String = "",
)

val listOfTeluguArtists = listOf<ProfileArtist>(
    ProfileArtist("Mani Sharma","Telugu", "3AMxH9QIPZbK8ND8C9j4Ss"),
    ProfileArtist("Devi Sri Prasad","Telugu", "5sSzCxHtgL82pYDvx2QyEU"),
    ProfileArtist("Thaman S","Telugu", "2FgHPfRprDaylrSRVf1UlN"),
    ProfileArtist("Anirudh Ravichander","Telugu", "4zCH9qm4R2DADamUHMCa6O"),
    ProfileArtist("Sid Sriram","Telugu","7qjJw7ZM2ekDSahLXPjIlN"),
    ProfileArtist("M.M.Keeravani", "Telugu","12l1SqSNsg2mI2IcXpPWjR"),
    ProfileArtist("Anup Rubens", "Telugu","05ppw2jwDfW9zo4mj6HNXp"),
    ProfileArtist("Mickey J. Meyer","Telugu","0WiZi3Q419nMpAQEqfBCbk"),
    ProfileArtist("Sunitha", "Telugu","74S8YRi4XQ5yf9ToSzuY80"),
    ProfileArtist("S.P. Charan", "Telugu","1BIAyVQxDuYrMqZ8H1Faja"),
    ProfileArtist("S.P. Balasubrahmanyam", "Telugu","2ae6PxICSOZHvjqiCcgon8"),
    ProfileArtist("Ilaiyaraaja","Telugu","3m49WVMU4zCkaVEKb8kFW7"),
    ProfileArtist("Harris Jayaraj", "Telugu","29aw5YCdIw2FEXYyAJZI8l"),
    ProfileArtist("Yuvan Shankar Raja", "Telugu","6AiX12wXdXFoGJ2vk8zBjy"),
    ProfileArtist("G.V. Prakash", "Telugu","5VVN3xZw1i2qihfITZlvCZ"),
    ProfileArtist("A.R. Rahman","Telugu","1mYsTxnqsietFxj1OgoGbG"),
)
val listOfHindiArtists = listOf<ProfileArtist>(
    ProfileArtist("Pritam","Hindi","1wRPtKGflJrBx9BmLsSwlU"),
    ProfileArtist("Amit Trivedi", "Hindi","7HCqGPJcQTyGJ2yqntbuyr"),
    ProfileArtist("Mithoon", "Hindi","09UmIX92EUH9hAK4bxvHx6"),
    ProfileArtist("Shankar-Ehsaan-Loy", "Hindi","0L5GV6LN8SWWUWIdBbTLTZ"),
    ProfileArtist("Vishal-Shekhar","Hindi","6Mv8GjQa7LKUGCAqa9qqdb"),
    ProfileArtist("Shreya Ghoshal","Hindi","0oOet2f43PA68X5RxKobEy"),
    ProfileArtist("Arijit Singh","Hindi","4YRxDV8wJFPHPTeXepOstw"),
    ProfileArtist("Javed Ali", "Hindi","4W91bbPB2CTSsHwt7eqNl7"),
    ProfileArtist("Mohit Chauhan", "Hindi","5GnnSrwNCGyfAU4zuIytiS"),
    ProfileArtist("Vishal Dadlani", "Hindi","6CXEwIaXYfVJ84biCxqc9k"),
    ProfileArtist("Neha Kakkar", "Hindi","5f4QpKfy7ptCHwTqspnSJI"),
    ProfileArtist("Badshah", "Hindi","0y59o4v8uw5crbN9M3JiL1"),
    ProfileArtist("Sachet Tandon", "Hindi","6WOdPJmexxFINcKMkP2jMG"),
    ProfileArtist("Dhvani Bhanushali", "Hindi","1OPqAyxsQc8mcRmoNBAnVk"),
    ProfileArtist("Himesh Reshammiya", "Hindi","0sSxphmGskGCKlwB9xa6WU"),
    ProfileArtist("Atif Aslam", "Hindi","2oSONSC9zQ4UonDKnLqksx")
)
val listOfEnglishArtists = listOf<ProfileArtist>(
    ProfileArtist("Adan Ricky","English","4D55EuvmIWO8OQ1AlhrZU4"),
    ProfileArtist("Justin Bieber","English","1uNFoZAHBGtllmzznpCI3s"),
    ProfileArtist("Selena Gomez","English","0C8ZW7ezQVs4URX5aX7Kqx"),
    ProfileArtist("Camila Cabello","English","4nDoRrQiYLoBzwC5BhVJzF"),
    ProfileArtist("Ariana Grande","English","66CXWjxzNUsdJxJ2JdwvnR"),
    ProfileArtist("Dua Lipa","English","6M2wZ9GZgrQXHCFfjv46we"),
    ProfileArtist("The Weeknd","English","1Xyo4u8uXC1ZmMpatF05PJ"),
    ProfileArtist("Kendrick Lamar","English","2YZyLoL8N0Wb9xBt1NhZWg"),
    ProfileArtist("Chris Brown","English","7bXgB6jMjp9ATFy66eO08Z"),
    ProfileArtist("Taylor Swift", "English","06HL4z0CvFAxyc27GXpf02"),
    ProfileArtist("Maroon 5", "English","04gDigrS5kc9YWfZHwBETP"),
    ProfileArtist("Shawn Mendes", "English","7n2wHs1TKAczGzO7Dd2rGr"),
    ProfileArtist("Jason Derulo", "English","07YZf4WDAMNwqr4jfgOZ8y"),
    ProfileArtist("Charlie Puth", "English","6VuMaDnrHyPL1p4EHjYLi7"),
    ProfileArtist("Mabel", "English","1MIVXf74SZHmTIp4V4paH4"),
    ProfileArtist("Ava Max", "English","4npEfmQ6YuiwW1GpUmaq3F")
)