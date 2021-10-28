# mycalorieninja
Java program to fetch nutritional info for a given food and prints it to a text file. Uses [CalorieNinja](https://calorieninjas.com/) API and Java 11 HTTPClient.
Make sure you pass your own API-KEY from CalorieNinja inside `local.properties` (NOTE ⚠ gitignore the file!). But I strongly recommend passing the key as ENV variable instead, and fetch it as `System.getenv("API-KEY")`
