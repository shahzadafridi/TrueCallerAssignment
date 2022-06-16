# TrueCallerAssignment
  The app should define and run 3 requests SIMULTANEOUSLY, each request is defined below

##### Tech Stack
- Kotlin
- MVVM Architecture
- Dagger Hilt
- Resource Class for Ui State
- LiveData
- Coroutine (ViewModelScope to trigger the calls)
- Jsoup (Third Party Library to get content from web url)

In the viewModel I am launching three coroutine SIMULTANEOUSLY for getting the content from the web url to solve the three different questions as per the requirements
```kotlin
fun onGetBlogContent() {
        viewModelScope.launch {
            _truecaller10thCharacter.value = UiState.Loading
            repository.getBlogContent { state ->
                when(state) {
                    is UiState.Success -> {
                        truecaller10thCharacterRequest(state.data.content)
                    }
                    is UiState.Failure -> {
                        _error.postValue(Pair("Truecaller10thCharacter",state.error))
                    }
                }
            }
        }
        viewModelScope.launch {
            _truecallerEvery10thCharacter.value = UiState.Loading
            repository.getBlogContent { state ->
                when(state) {
                    is UiState.Success -> {
                        truecallerEvery10thCharacterRequest(state.data.content)
                    }
                    is UiState.Failure -> {
                        _error.postValue(Pair("TruecallerEvery10thCharacter",state.error))
                    }
                }
            }
        }
        viewModelScope.launch {
            _truecallerWordCounter.value = UiState.Loading
            repository.getBlogContent { state ->
                when(state) {
                    is UiState.Success -> {
                        truecallerWordCounterRequest(state.data.content)
                    }
                    is UiState.Failure -> {
                        _error.postValue(Pair("TruecallerWordCounter",state.error))
                    }
                }
            }
        }
    }
```
### 1. Truecaller10thCharacterRequest

- Grab https://blog.truecaller.com/2018/01/22/life-as-an-android-engineer/ content from the web
- Find the 10th character and display it on the screen

As per the question requirement that disply the 10th char from the content. String start from 0 index so that was the reason to picking up the 9th index which means the 10th char of string
```kotlin
fun truecaller10thCharacterRequest(data: String){
        _truecaller10thCharacter.postValue(UiState.Success(data.get(9)))
}
```

### 2. TruecallerEvery10thCharacterRequest
-  Grab https://blog.truecaller.com/2018/01/22/life-as-an-android-engineer/ content from the web
-  Find every 10th character (i.e. 10th, 20th, 30th, etc.) and display the array on the screen

  **Method 1** is more effiecent than **Method 2** because the number of iteration based on calculated limit e-g If the chars are **600** then we need to iterate only **60 times** to find the 10th char whereas **Method 2** encourage to iterate on each charcters so that is mean the loop will run up to **600 times** if the condition of index mathcing with 10th position so that will aslo cost to time and complexity

```kotlin
fun truecallerEvery10thCharacterRequest(data: String) {
        val limit = data.length/10 // Finding iteration limit
        var method1Str = ""
        var tenthIndexCounter = 9 // As string start from 0 index so 10 - 1 = 9
        for (i in 0 until limit){
            method1Str += data[tenthIndexCounter]
            tenthIndexCounter += 10
        }

        _truecallerEvery10thCharacter.postValue(UiState.Success(method1Str))

        //Not using below code just mentioning second method
        var method2Str = ""
        for (index in 1 until limit){
            if (index % 10 == 0){
                method2Str += data[index-1]
            }
        }
    }
```

 ### 3. TruecallerWordCounterRequest
- Grab https://blog.truecaller.com/2018/01/22/life-as-an-android-engineer/ content from the web
- Split the text into words using whitespace characters (i.e. space, tab, line break, etc.), count the occurrence of every unique word (case insensitive) and display the count for each word on the screen

As per the question requirement, **spliting** the string with the help of empty space then grouping them with the help of extension function of **collection groupBy{ word }** then finding its **count**.

```kotlin
fun truecallerWordCounterRequest(data: String){
        val words = data.split(" ")
        val groups = words.groupBy { it }
        var words_count = ""
        groups.forEach { s, list ->
            words_count += String.format("%s = %d",s,list.size) + "\n"
        }
        _truecallerWordCounter.postValue(UiState.Success(words_count))
    }
```
