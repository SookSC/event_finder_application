# EventHub

EventHub is an Android application that brings local events from multiple sources into one place.
Instead of searching across different platforms, users can quickly find and save nearby events through a single, intuitive interface.

Built to demonstrate full-cycle Android development, including API integration, state management, and modern UI practices.

## 🎥 Live Preview

https://github.com/user-attachments/assets/fedecf8f-8cfc-4c3c-82ae-6f59458d129a

> EventHub (in-progress): Splash screen, location access, dynamic event loading.

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
