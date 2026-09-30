# TV Scheduling Checklist (Web App)

A lightweight, responsive web application for television production teams to plan broadcast programmes and track production readiness checklists.

## Features
- **Production Dashboard**: Live stats for total programmes, prep status, on-air broadcasts, and checklist compliance percentage.
- **Interactive 6-Step Checklist**:
  1. Script completed
  2. Talent confirmed
  3. Studio booked
  4. Video / assets ready
  5. Approved
  6. Published / On Air
- **Dual Views**:
  - **List View**: Chronological programme cards with quick status changer, checklist toggles, progress bars, and notes.
  - **Calendar View**: Interactive month grid with day counts and scheduled programme breakdowns.
- **Search & Filters**: Search by programme title, notes, or PIC. Filter by Channel (`TV3`, `8TV`, `TV9`, `NTV7`, `DS`), Date, and Status (`Draft`, `In Progress`, `Ready`, `On Air`, `Completed`).
- **Offline Local Storage**: Automatically saves all changes to browser `localStorage`.
- **Pre-Seeded Sample Data**: Realistic Malaysian broadcast programmes included, with a 1-click reset button.

## How to Run
Simply open `index.html` in any browser:
- Double-click `index.html` in your file explorer, OR
- Serve with any static web server:
  ```bash
  python3 -m http.server 8080
  ```
  Then visit `http://localhost:8080` in your browser.
