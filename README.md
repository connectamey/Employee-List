# Fetch Rewards Coding Exercise - Mobile

This repository contains the native Android application written in Kotlin that meets the
requirements outlined by the Fetch Rewards Coding Exercise for Software Engineering - Mobile.

## Overview

The app retrieves data from the Fetch Rewards API, filters, sorts, and groups the items according to
the specified criteria:

- Items are grouped by "listId".
- Results are sorted by "listId" and within each group sorted by "name".
- Items with a blank or null "name" are filtered out.
- Data is displayed to the user in an easy-to-read list.

## Building the Project

To build the project:

1. Ensure you have Android Studio Arctic Fox or later installed.
2. Clone this repository.
3. Open the project in Android Studio.
4. Sync the project with Gradle files.
5. Run the project on an emulator or a real device running the current release of the Android OS.

## Architecture

The project follows the MVVM (Model-View-ViewModel) architecture and uses the following components:

- Retrofit for network requests.
- Coroutines for asynchronous operations.
- LiveData for updating the UI with the data changes.
- A Repository pattern for abstracting the data layer.

## Libraries Used

- [Retrofit](https://square.github.io/retrofit/) for REST api communication
- [Gson](https://github.com/google/gson) for serialization/deserialization of JSON
- [Coroutine](https://kotlinlang.org/docs/coroutines-overview.html) for managing background threads

## Screenshots

Include a few screenshots of the app's UI here.

## Author

- **Amey Bansod** - *Initial work* - [AmeyBansod](https://github.com/connectamey)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

- Fetch Rewards Recruiting Team for providing the opportunity.
- All third-party libraries and tools used in the project.

## Contact

For questions about this project, please contact ameygotemail@gmail.com.

## Additional Notes

Any additional information about the project can be mentioned here.
