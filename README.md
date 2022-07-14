# Submission-BFAA3-Dicoding

Submission untuk kelas Belajar Fundamental Aplikasi Android di [Dicoding](https://www.dicoding.com). Bahasa yang digunakan adalah Kotlin. 
Submission ini adalah sebuah aplikasi yang bertemakan GitHub User.

### Perhatian!!!

> CAUTION : Repository ini dibuat sebagai bahan refrensi untuk Kelas Dicoding [Belajar Fundamental Aplikasi Android](https://www.dicoding.com/academies/14) 
agar dapat membantu teman-teman dalam menyelesaikan submission yang sedang dikerjakan. Ingat untuk tidak mengcopy-paste saja tanpa memahami skema programmingnya.

## Custom UI

- [Glide](https://github.com/bumptech/glide)
- [CircleImageView](https://github.com/hdodenhof/CircleImageView)

## Preview
<img src="https://user-images.githubusercontent.com/73926625/178882716-15c2ad16-9663-46c2-8267-1d0791508acc.png" width="70%">
<img src="https://user-images.githubusercontent.com/73926625/178882798-8bbb80b9-a6d5-4263-a2db-e7d9e5368b77.png" width="70%">

## If you want to clone this app
You need to update GitHub token in Constants class at com.gnacoding.submissionbfaa.utils
```
object Constants {
    const val GITHUB_TOKEN = "put_your_github_token_here"
    
    ...
}
```

### Secure Token 🛡️
Or you can secure your token, you can put in local.properties then rebuild the project. Example:
```
GITHUB_TOKEN = ghp_123456789
```
then add this to build.gradle(app) to access your token from local.properties:
```
android {
    ...
    
    defaultConfig {
        ...
        Properties properties = new Properties()
        properties.load(project.rootProject.file('local.properties').newDataInputStream())

        buildConfigField("String", "GITHUB_TOKEN", "\"${properties.getProperty('GITHUB_TOKEN')}\"")
    }
    ...
}
```
Last, you can access it in Constants.kt
```
object Constants {
    const val GITHUB_TOKEN = BuildConfig.GITHUB_TOKEN
    
    ...
}
```

## Certificate

[Download Certificate](https://www.dicoding.com/certificates/NVP714YNRPR0)

## Author

[Abdul Ghany At-Tirmidzi](https://www.linkedin.com/in/abghany/) - aghany08@gmail.com
