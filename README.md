# Event Finder Application

An Android application that gathers and displays upcoming events near the user, allowing them to browse, filter and save events.


## Setup Instructions
### API key setup
To retrieve local event data, the PredictHQ and Ticketmaster APIs are used.
Please obtain API authentication keys for both APIs and complete the setup steps below.
1. Create a 'secrets.properties' file in the project root.
2. Add the following line:
```plaintext
PREDICTHQ_API_KEY=your_api_key_here
TICKETMASTER_API_KEY=your_api_key_here
```  
