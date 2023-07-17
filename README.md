# Graceful Shutdown Issue

## Problem

When we have a **background** service and trigger `onTaskRemoved`, background threads have not enough time to complete. The OS almost immediatelly kills the process when a thread switch happens.

However, when we have a **foreground** service, this is not the case, the background threads have enough time to complete.

<img src="https://github.com/aaronfodor/graceful-shutdown-issue/assets/37120889/b6060153-dfa6-4b4e-8ea5-109a58ff4514" alt="" height="400" />