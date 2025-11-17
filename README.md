# Practical 7 — SQLite + JSON Person Manager App

A simple Android application that demonstrates **SQLite database operations** and **JSON API integration** for managing person data.

---

## Features
- **View Data** from SQLite and JSON API (combined list)
- **Add Person** with name, email, phone, and address
- **Delete Person**
  - SQLite entries → removed from database  
  - JSON entries → removed from list only
- **Auto Refresh** when returning to main screen

---

##  How It Works

###  Main Screen (MainActivity)
- Loads persons from **SQLite** + **JSON API**
- Displays them in a **RecyclerView** with:
  - Name, phone, email, address
  - Pink delete button
- “Register” button navigates to registration screen

###  Registration Screen (RegisterActivity)
- User enters: Name, Email, Phone, Address  
- Tap **Submit** → Data saved in SQLite  
- Toast confirmation  
- Returning to main screen auto-refreshes data

---

##  OUTPUT

<img src="https://drive.google.com/uc?export=view&id=1MqV22hhmzGPy1yY1_lEZktFKW2A3vAMh" width="300" />
<br><br>
<img src="https://drive.google.com/uc?export=view&id=1WyyOgJPwAi3WCjoPm_W8XMhAKgSwpSIZ" width="300" />




