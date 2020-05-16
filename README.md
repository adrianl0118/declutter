## About

An android mobile app that connects users that need various items with people who are giving those things away. Ads are created by other users and placed in a cloud database hosted on Google Firebase and any user can browse all available ads on the app.

## Technical

Standard single application architecture with multiple fragments--the main activity opens and maintains the connection to Firebase. App is written in Kotlin.

## Limitations

App does not yet have a "delete posts" function to get rid of items that have been picked up. Also, input validation and database security settings are quite crude and would require improvement in addition to migration to a production-level database in order for this app to be put to use.

