Sample Android app to demonstrate `WorkManager` (`1.0.0-alpha04`) bug ([Issue 111195153]).

> Requesting a worker from a running worker causes the first worker to run again right away, which
> will essentially cause both workers to continually execute over and over again forever (even if
> the periodic worker has been scheduled to run aprox. every 12 hours).

## Timeline

### Expected

![Expected Timeline](artwork/timeline_expected.png)

### Actual

![Actual Timeline](artwork/timeline_actual.png)

## Behavior

| Expected | Actual |
|-|-|
| `PeriodicWorkRequest`-based `Worker` to run every 12 hours | `PeriodicWorkRequest`-based `Worker` runs over and over again <sup>1</sup> |
| `RUNNING` → `ENQUEUED` (for ~12 hours) → `RUNNING` → `ENQUEUED` (for ~12 hours) → ... | `RUNNING` → `RUNNING` → `RUNNING` → ... |
| ![Expected Behavior](artwork/behavior_expected.gif) | ![Actual Behavior](artwork/behavior_actual.gif) |


<sup>1</sup> _When another `Worker` is started from the original `PeriodicWorkRequest`-based `Worker`._

[Issue 111195153]: https://issuetracker.google.com/issues/111195153
