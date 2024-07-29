<img src="https://github.com/o7Moon/OpenBoatUtils/blob/main/src/main/resources/assets/openboatutils/OpenBoatUtils.png?raw=true" width = 300>

# OpenBoatUtils (obu)
OpenBoatUtils (obu) is an API designed to make boat physics in Minecraft highly customizable. It allows servers to control and modify various boat settings, but it does not perform many actions on its own. In singleplayer, you can use commands to edit these settings, while in multiplayer, the server has control via Plugin Messages.

## How It Works:
OpenBoatUtils provides an interface for server administrators to adjust boat physics parameters. This API communicates with the server to update boat behavior in real-time, ensuring that all players experience the same physics adjustments. While OpenBoatUtils offers extensive customization options, its primary function is to serve as a tool for servers to implement these changes, rather than acting independently.

## Features:
- **Configurable step height:** Adjust how boats handle steps and inclines.
- **Configurable slipperiness:** Modify the friction and sliding behavior of boats.
- **Configurable "Air Control":** Control how boats behave when airborne.
- **Configurable Boat Jumping:** Enable or adjust the ability for boats to jump.
- **Configurable gravity:** Set the gravity affecting boats.
- **Configurable acceleration values:** Customize how quickly boats accelerate.
- **Additional customizable settings:** Explore more obscure and advanced settings for fine-tuning boat physics.

For more detailed information about the features and how they work, visit the [wiki](https://github.com/o7Moon/OpenBoatUtils/wiki).

For plugin developers, the wiki also includes [documentation for all of the packets](https://github.com/o7Moon/OpenBoatUtils/wiki/Packets).

## Installation:
To use OpenBoatUtils, you must have the [Fabric API](https://modrinth.com/mod/fabric-api) installed. Follow these steps to install obu:
1. Download the latest version of OpenBoatUtils from the releases page.
2. Ensure the Fabric API is installed on your server.
3. Place the OpenBoatUtils JAR file into your server's mods folder.
4. Start your server to load OpenBoatUtils.

## Usage:
Once installed, server administrators can use commands to adjust boat settings. In singleplayer, use the same commands to modify settings locally. Detailed command usage can be found on the [wiki](https://github.com/o7Moon/OpenBoatUtils/wiki).

> [!IMPORTANT]
> Requires [Fabric API](https://modrinth.com/mod/fabric-api).

## Credits:
- Mojang, for showcasing boat stepping in 1.19.4-pre1.
- BoatHub, for creating the original BoatUtils.

> Note: This README also appears on Modrinth. It is important to communicate that OpenBoatUtils (obu) is essentially an API for servers to use and doesn't do much on its own.
