# Orion Account Creator (OAC)

## Overview
The Orion Account Creator was a project designed to fuel the massive bot farm effort with accounts. Without accounts, no bot farm
can possibly run. Accounts are to a bot farm as oil is to a car. The OAC utilizes image recognition via the Sikuli library, and
automates the account creation process through the [Runescape NXT](http://services.runescape.com/m=news/nxt--new-game-client--now-live-for-everyone) game client.

## Features
- Utilizes image recognition instead of hardcoded coordinates to navigate through game client
- Caches subregions for fast execution time
- Automatically stores accounts in remote MySQL database
  - Falls back on local disk storage if necessary
- Generates random account names
