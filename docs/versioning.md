# Versioning

## Version name

A string used as the version number shown to users.

### Components

`<platform>-<major>.<minor>.<patch>-<optional pre-release channel><optional number of pre-release build in channel>`

- Platform: `android`, `desktop`, `ios`, `wearos`
- Major
- Minor
- Patch
- (Optional) Pre-release channel: `dev`, `alpha`, `beta`, `rc`
- (Optional) Number of pre-release build in channel: For `dev`, `alpha`, `beta`, `rc` specify number of build in XX format starting in 1, `dev01`, `alpha04`, ...

Example: `android-1.0.0-dev02`,  `wearos-2.1.3-beta04`, `desktop-1.2.0`

## Version code

A positive integer used as an internal version number.

Transform the version name into a unique version code.

### Platform

| platform | integer |
|--|--|
| android | 1 |
| desktop | 2 |
| ios | 3 |
| wearos | 4 |

### Major, minor, patch

| major.minor.patch | integer |
|--|--|
| 1.2.0 | 120 |

### Pre-release channel

| channel | integer |
|--|--|
| dev | 0 |
| alpha | 1 |
| beta | 2 |
| rc | 3 |
| stable | 4 |

### Number of pre-release build in channel

Maintain XX format for number, `dev02` -> `02`

### Example

| versionName | versionCode |
|--|--|
| `android-1.0.0-dev02` | 1100002 |
| `wearos-2.1.3-beta04` | 4213204 |
| `desktop-1.2.0` | 2120400 |
