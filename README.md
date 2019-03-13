# Movie Bucket
Movie bucket is an Android application for discovering movies and saving them to a "bucket list." The app utilizes [The Movie Database](https://www.themoviedb.org/) for data.
### Movie bucket is still in active development
Key features:
* Discover/search for movies
* Discover upcoming movies
* Bookmark upcoming movies to bucket list
* Recieve notifications when movies from bucket list are releasing to theaters or retail

## Building
You need an TMDB API key to build and run the app.
* If gradle.properties does not exist in project root, create the file
* Set the property "TMDB_API_KEY" with your API key
`TMDB_API_KEY="..."`

### Libraries used:
* Dagger
* Android Architecture Components
* Retrofit
* Gson
* Glide
* Timber
* Butterknife
* AssertJ and Roboletric are used along with JUnit for testing