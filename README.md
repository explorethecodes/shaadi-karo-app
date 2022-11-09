# Shaadi Karo

Shaadi Karo App is an Android native app developed in Kotlin.

It will be showing the suggestions for matching personal profiles in a list which can be accepted/declined according to the users wish.

## Features

### Accept/Decline Profile

> User can Accept/Decline profiles and the decision is stored in the database
   
### Change Action

> Users can change their mind and change the Accept/decline action any time

### Full screen Photo Zoom

> View profile photo in full screen mode with zoom support

### Offline support

> Works without internet using AppDatabase

### Latest Profiles Network Fetch

> Manually fetch new profiles from network by refresh button or swiping down to refresh

## Structure
`Hilt Dependency Injection`

The dagger 2 framework (Hilt) is used for the dependency injection.

`Model-View-View-Model (MVVM)`

The code is designed in MVVM with View Model & Live data.

`Repository`

Repository pattern is used for the flow of data. All the local and remote data coming in and out is passed via repositories and it is considered as the data store. 

`Room Database`

The Jet pack component Room Db is used as the App database. Type converters are also used to store different kinds of data in Room Db.

`Data binding`

Data binding is used to bind the data to the view automatically from the xml which makes a very cleaner approach with less codes

`View Binding`

View binding is enabled in order to handle the Xml ids without conflicts. It reduces boiler plate codes & hence reduces code redundancy.

`Retrofit`

The Api & network areas are handled by the retrofit library. All the network scenarios are handled separately and updated in the UI as well.

`Support Module`

An extra layer of support Module is added with App Module in order to isolate the reusable codes, that can be used on other projects as well.

`Libraries`

The other third party libraries dependent on includes,
1. Glide - Image loading library
2. Loupe - Image zooming library
