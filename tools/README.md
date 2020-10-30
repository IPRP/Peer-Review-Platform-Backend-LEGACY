# Java ☕

## 🛠 Installation

## Linux + WSL2

```bash
chmod +x installjava.sh
sudo ./installjava.sh
```

## Windows

```
.\installjava.bat
```



# MongoDB 🍃

## 🛠 Installation

### Linux + WSL2

> Note: The following script is only supported on Ubuntu

```bash
chmod +x installmongodb.sh
sudo ./installmongodb.sh
```

### Windows

> Note: When prompted by the installer choose to install MongoDB as a service

```bash
.\installmongodb.bat
```

## 🏃‍♂️ Run

### Linux

```bash
chmod +x runmongodb.sh
./runmongodb.sh
```

### WSL2 + Windows

```
.\runmongodb.bat
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