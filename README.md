# InterviewTaskCandySpace

This repository contains an android app for interview purpose. It implements MVVM architecture using Koin ID, Retrofit, Coroutine, ViewModel, CardView, LiveData and Recyclerview.
<p align="center">
  <img src="Screen1.jpeg" width="250"/>
  <img src="Screen2.jpeg" width="250"/>
  <img src="Screen3.jpeg" width="250"/>
</p>
<br>
<br>

#### The app has following packages:
1. **adapters**: It contains the recyclerview adapter.
2. **di**: Dependency providing classes using Koin.
3. **model**: It contains all the data accessing and manipulating components.
4. **rest**: It contains the repository and api endpoint declarations
5. **utils**: It contains the utility classes and pagination listener.
6. **view**: View classes along with their corresponding ViewModel.
7. **viewmodel**: It contains the corresponding ViewModel.

#### !Important: UI & Unit Testing:

! Important - Before start testing please uncomment the testing code lines 36 and comment 41-47 in UserViewModel.kt.(https://github.com/KesavanPanneerselvam/InterviewTaskCandySpace/blob/master/app/src/main/java/com/android/interviewtask/candyspace/ui/userslist/UserViewModel.kt)
