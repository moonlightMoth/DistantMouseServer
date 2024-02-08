<a name="readme-top"></a>
![GitHub Actions Workflow Status](https://img.shields.io/github/actions/workflow/status/moonlightmoth/DistantMouseServer/maven.yml)
![GitHub Release](https://img.shields.io/github/v/release/moonlightmoth/DistantMouseServer)
![Static Badge](https://img.shields.io/badge/license-MIT-blue)


<!-- PROJECT LOGO -->
<!--
<br />
<div align="center">
  <a href="https://github.com/othneildrew/Best-README-Template">
    <img src="images/logo.png" alt="Logo" width="80" height="80">
  </a>
  <h3 align="center">Distant Mouse Server</h3>
</div>
-->


<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about">About</a>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li> <a href="#built">Build</a></li>
        <li><a href="#usage">Usage</a></li>
      </ul>
    </li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

This is a server part of DistantMouse project which allows you to emulate mouse actions over TCP/IP. By now there is only one [client][distant-mouse-client-url] for Andoird

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- GETTING STARTED -->
## Getting Started

### Prerequisites

For manual build you need:
* Java 11+
* Maven

Usage from jar requires only Java 11+.
  
### Build
Currently built jar located in target directory but you may build it yourself using following commands:
```
git clone https://gitnub.com/moonlightmoth/DistantMouseServer.git
cd DistantMouseServer
mvn package
```
Compiled jar will be inside target directory.

<!-- USAGE EXAMPLES -->
### Usage
Place build artifact to any location you want and run it using cmd or any shell you like.

`java -jar DistantMouseServer-<VERSION>.jar`

For further information visit client [repository][distant-mouse-client-url].
<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- CONTRIBUTING -->
## Contributing

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/feature`)
3. Commit your Changes (`git commit -m 'Added feature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTACT -->
## Contact

* Google jyhgftm@gmail.com
* [Telegram](https://t.me/moonlightmoth)
<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[build-url]:https://img.shields.io/github/actions/workflow/status/moonlightMoth/DistantMouseServer/maven.yml

[distant-mouse-client-url]: https://github.com/moonlightmoth/InpCtrlClient
