# **Event Management System**

## 📌 **Overview**
The Event Management System is a modular and scalable platform designed to manage sports events efficiently. It includes functionalities for event creation, participant registration, venue allocation, fixture generation, and tournament handling.

---

## 📊 **Complete System Schema**
📌 **Diagram:**
![Editor _ Mermaid Chart-2025-04-02-110747](https://github.com/user-attachments/assets/42706a57-0aa5-4181-afd3-f3e8b8cfc781)


---

## 🏗️ **System Architecture**
The system is divided into the following key sub-schemas:

### **1️⃣ Event Management Sub-schema**
- **Handles**: Event creation, removal, and status tracking.
- **Key Components:**
  - `EventManager`: Manages all events and venues.
  - `Event`: Abstract class for generic events.
  - `CricketEvent` & `BadmintonEvent`: Specialized event types.
  - `EventFactory`: Creates event instances based on type.

📌 **Diagram:**
![Editor _ Mermaid Chart-2025-04-02-110949](https://github.com/user-attachments/assets/0ded82b0-a727-42d5-a81e-b0fa8072d2bc)


---

### **2️⃣ Registration Management Sub-schema**
- **Handles**: Individual and team-based registrations.
- **Key Components:**
  - `Registration`: Abstract base class.
  - `IndividualRegistration`: For single-player events.
  - `TeamRegistration`: For team-based events (e.g., Cricket, doubles Badminton).
  - **Validation**: Ensures correct registration types per event.

📌 **Diagram:**
![Editor _ Mermaid Chart-2025-04-02-111111](https://github.com/user-attachments/assets/624d8b44-f6fb-434f-af08-eed54b73e169)


---

### **3️⃣ Venue Management Sub-schema**
- **Handles**: Venue availability, selection, and booking.
- **Key Components:**
  - `Venue`: Represents an event location with capacity and availability tracking.
  - `VenueAssignmentHandler`: Ensures venues are assigned only after registration closes.

📌 **Diagram:**
![Editor _ Mermaid Chart-2025-04-02-111308](https://github.com/user-attachments/assets/8cadeb8e-1ec3-43ad-a907-54a577beb9da)


---

### **4️⃣ Fixture & Match Management Sub-schema**
- **Handles**: Fixture generation, match scheduling, and winner tracking.
- **Key Components:**
  - `Match`: Represents a match between two teams.
  - `FixtureGenerator`: Generates match fixtures based on registrations.

📌 **Diagram:**
![Editor _ Mermaid Chart-2025-04-02-111426](https://github.com/user-attachments/assets/1cf298f4-4dfe-48cf-8e0a-6bb7560c6748)


---

### **5️⃣ Tournament Handling Sub-schema**
- **Handles**: Managing tournament progress, results, and rankings.
- **Key Components:**
  - `TournamentHandler`: Manages match outcomes, declares winners, and handles drawn matches.

📌 **Diagram:**
![Editor _ Mermaid Chart-2025-04-02-111527](https://github.com/user-attachments/assets/34e68c7f-5b77-4115-aa6e-8a1a95b14176)


---

## 🎭 **Use Case Diagram**
📌 **Diagram:**
![diagram-export-31-03-2025-09_49_38](https://github.com/user-attachments/assets/fdad699f-e750-43ab-9827-8dfe5e04e644)


---

## 🛠 **Installation & Setup**
1. Clone the repository:
   ```sh
   git clone https://github.com/yourusername/event-management-system.git
   ```
2. Navigate to the project directory:
   ```sh
   cd event-management-system
   ```
3. Compile and run the project:
   ```sh
   javac -d bin src/**/*.java
   java -cp bin MainClass
   ```

---

## 🚀 **Features**
✅ Modular event management system
✅ Individual & team-based registration
✅ Automatic venue assignment
✅ Dynamic fixture generation
✅ Tournament progress tracking

---

## 📖 **Usage**
- **Create an Event:** `EventManager.addEvent(new CricketEvent(...));`
- **Register a Participant:** `event.registerParticipant(new TeamRegistration(...));`
- **Assign a Venue:** `VenueAssignmentHandler.assignVenue(eventId, venue);`
- **Generate Fixtures:** `event.generateFixtures(venueList);`

---

## 👥 **User Stories**
📌 **Event Organizer:**
- As an event organizer, I want to create and manage different sports events so that I can handle multiple tournaments efficiently.

📌 **Participant:**
- As a participant, I want to register as an individual or as part of a team so that I can join my preferred events.

📌 **Venue Manager:**
- As a venue manager, I want to allocate and track venue availability so that events are scheduled properly.

📌 **Tournament Manager:**
- As a tournament manager, I want to generate fixtures and track match results so that tournaments proceed smoothly.

---

## 🤝 **Contributing**
We welcome contributions! Feel free to submit issues and pull requests.

---

## 📜 **License**
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

