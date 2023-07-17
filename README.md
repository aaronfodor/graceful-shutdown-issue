# Graceful Shutdown Issue

## Problem

When we have a **background** service and trigger `onTaskRemoved`, background threads have not enough time to complete. The OS almost immediatelly kills the process when a thread switch happens.

However, when we have a **foreground** service, this is not the case, the background threads have enough time to complete.

<img src="https://ghe.spotify.net/storage/user/9845/files/3fc18712-f8a4-4e9b-bed7-68a7219df795" alt="" height="400" />