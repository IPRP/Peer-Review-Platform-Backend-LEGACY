# Java ☕

## 🛠 Installation

## Linux + WSL2

```bash
chmod +x install_java.sh
sudo ./install_java.sh
```

## Windows

```
.\install_java.bat
```



# MongoDB 🍃

## 🛠 Installation

### Linux + WSL2

> Note: The following script is only supported on Ubuntu

```bash
chmod +x install_mongodb.sh
sudo ./install_mongodb.sh
```

### Windows

> Note: When prompted by the installer choose to install MongoDB as a service

```bash
.\install_mongodb.bat
```

## 🏃‍♂️ Run

### Linux

```bash
chmod +x run_mongodb.sh
./run_mongodb.sh
```

### WSL2 + Windows

```
.\run_mongodb.bat
```

## ❌Uninstall

### Linux + WSL2

```bash
sudo apt-get remove mongodb
```

### Windows

```bash
sc.exe delete MongoDB
```



## 📕 Sources

* https://github.com/ojdkbuild/ojdkbuild
* https://docs.mongodb.com/manual/tutorial/install-mongodb-on-windows/
* https://docs.mongodb.com/manual/tutorial/install-mongodb-on-ubuntu/