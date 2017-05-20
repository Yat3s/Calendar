# Calendar
A nice interaction calendar widget containing lists of agenda events and weather conditions.

### Priview
![screenshot](https://cloud.githubusercontent.com/assets/14801837/26261424/a228a836-3d03-11e7-8d32-b595b7514ab1.png)

### Compile & Build
```
$ git clone https://github.com/Yat3s/Calendar.git
$ cd Calendar
$ git checkout dev
$ ./gradlew build
```

### Download
You can download from [Release 1.0.0](https://github.com/Yat3s/Calendar/releases/download/1.0.0/Calendar.apk)

### Specification
**Calendar**
-  `CalendarView`:  A custom calendar view contains week indicator and calendar date list,  also contains some APIs for date select and list update:
	- `expand()` and `collapse()`  is a method for process calendar view state, It will update row count to `CALENDAR_EXPANSION_ROW` while expand and update row count to `CALENDAR_FOLD_ROW` while collapse.
	- `setOnItemSelectedListener()` is a trigger for item has be selected, **But it is ONLY trigger from user click item.**  
	- `updatedCurrentSelectedItem()` update current highlight/checked item and uncheck last checked item.
- `CalendarAdapter`: A custom `RecyclerViewAdapter`,  contain all iteRetrieve_data_from_repository.png
                                                                     Process_request_and_response.pngms data bind.

**Agenda**
- `AgendaView`: A custom agenda view contains a event list and recent weather data.
	- `scrollToPosition()` is method for agenda scroll to target position
	- `setOnAgendaScrollListener()`is a listener of agenda recycler view, it contains some APIs for scroll listen.
- `CalendarDataSource` you can get all calendar and agenda event data from this.

**Weather**
- It retrieve all weather data from [Darksky](https://darksky.net),  and it will `requestLastKnownLocation` for determine where you are.
- You can view `WeatherDataSource`and all weather data can get from it.

**Event**
- `NewEvent`:  Sorry for that , I don't have enough time to implement all logics, so it's just a UI layer.
- `EventDetail`:  Get event from selected agenda item and show event detail, but it is not completely.

**DataRepository**
- This is a data repository defined all `Observable`for retrieve data.
- It contains some `Subscription` to process retrieve data from local cache or remote, you need not know data source come from.

**RestClient**
- This is a HTTP/HTTPS client for network requests, it can highly expanded.


### Protocol
#### Network
Retrieve data from repository:
![image](https://cloud.githubusercontent.com/assets/14801837/26238642/7e58389c-3cac-11e7-89aa-9dea0a0eebdc.png)

Process request and response:
![image](https://cloud.githubusercontent.com/assets/14801837/26238781/24a6a8dc-3cad-11e7-934a-e9bfdd3a2565.png)
#### Persistent
Because data is too sparse and It only storage on `SharePreference`.

### 3rd party
**RxJava**  
RxJava is a Java VM implementation of Reactive Extensions: a library for composing asynchronous and event-based programs by using observable sequences.  
Ref: https://github.com/ReactiveX/RxJava

**Retrofit**  
A type-safe HTTP client for Android and Java by Square, Inc. and It is flexible for add interceptor and converter.  
Ref: https://github.com/square/retrofit

**Butterknife**  
Bind Android views and callbacks to fields and methods,  it can simplify many find view code with some plugin.  
Ref: https://github.com/JakeWharton/butterknife

**Iconify**  
Iconify offers you a huge collection of vector icons to choose from, and an intuitive way to add and customize them in your Android app.  
Ref: https://github.com/JoanZapata/android-iconify

### TODOs and Issues
- Notify data after added event.
- Strongly test code.
Others issues remain to resolve and it publish to :[Github Issues](https://github.com/Yat3s/Calendar/issues)

### Bugs and Feedback
It 's my pleasure to ask questions or report bugs or discussions for me, you can tell me from [Github Issues](https://github.com/Yat3s/Calendar/issues)

### demonstrate
![preview](https://cloud.githubusercontent.com/assets/14801837/26260749/005f4f7a-3d01-11e7-962f-55cfaa099c76.gif)

### Others
I hope I can get into the next round of interviews, **I love Microsoft**~lol
