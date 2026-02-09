<div align="center">

# 🎓 SISTEMA DE GESTIÓN UNIVERSITARIA

### Trabajo Final - Desarrollo de Aplicaciones Distribuidas (DAD)

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Eclipse](https://img.shields.io/badge/Eclipse-2C2255?style=for-the-badge&logo=eclipse&logoColor=white)
![GitKraken](https://img.shields.io/badge/GitKraken-179287?style=for-the-badge&logo=gitkraken&logoColor=white)
![Sockets](https://img.shields.io/badge/Sockets-TCP%2FIP-blue?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Finalizado-brightgreen?style=for-the-badge)
<br>

<p align="center">
  <b>Una solución robusta Cliente-Servidor para la administración académica distribuida.</b><br>
  Implementación de sockets nativos, serialización de objetos y protocolos concurrentes.
</p>

</div>

---

## 📋 Descripción del Proyecto

Este proyecto consiste en el desarrollo de una aplicación distribuida que simula la infraestructura de gestión de una Universidad. El objetivo es permitir que múltiples sedes (Clientes) se conecten a un sistema central (Servidor) para realizar operaciones administrativas en tiempo real.

El sistema implementa un **protocolo de comunicación personalizado** basado en TCP/IP, gestionando la concurrencia y la transmisión de datos complejos mediante flujos de objetos (`ObjectStream`).

---

## ⚙️ Arquitectura y Protocolo

El sistema se basa en un diseño de **Doble Canal** para optimizar el tráfico de red y evitar bloqueos en el servidor principal.

<div align="center">

| Canal | Puerto | Tipo | Función Principal |
| :---: | :---: | :---: | :--- |
| **Comandos** | `5000` | Texto | Envío de instrucciones (Login, Logout, Peticiones). |
| **Datos** | `5001+` | Objetos | Transferencia de listas y entidades (Serialización). |
| **KeepAlive** | `4000` | Señal | (Opcional) Heartbeat para control de sesiones activas. |

</div>

### 🔄 Flujo de Comunicación
1. **Auth:** El cliente se autentica (`USER`/`PASS`).
2. **Request:** Envía un comando por el puerto 5000 (ej. `LIST TIT`).
3. **Handshake:** El servidor responde `PREOK` y asigna un puerto efímero (ej. 5005).
4. **Data:** Se establece una conexión paralela en el puerto 5005 para transmitir el objeto.
5. **Close:** Se finaliza la transmisión y se libera el puerto de datos.

---

## 🚀 Funcionalidades

El sistema cuenta con un control de acceso (Usuario: `admin` / Clave: `admin`) y gestiona las siguientes entidades:

### 🎓 Gestión de Títulos (Grados)
- [x] **Añadir Título:** `ADD TIT`
- [x] **Actualizar:** `UPDATE TIT`
- [x] **Consultar:** `GET TIT`
- [x] **Eliminar:** `REMOVE TIT`
- [x] **Listar Todos:** `LIST TIT`

### 📚 Gestión de Asignaturas
- [x] **Añadir Asignatura:** `ADD ASIG`
- [x] **Vincular a Título:** `ADD ASIG2TIT`
- [x] **Eliminar:** `REMOVE ASIG`
- [x] **Listar:** `LIST ASIG`

### 📝 Gestión de Matrículas
- [x] **Matricular Alumno:** `ADD MATRICULA`
- [x] **Modificar:** `UPDATE MATRICULA`
- [x] **Consultar:** `GET MATRICULA`

---

## 🛠️ Tecnologías y Herramientas

<div align="center">

| Tecnología | Uso en el proyecto |
| :--- | :--- |
| **Java SE 21** | Lenguaje principal del desarrollo. |
| **Java.net.Socket** | Comunicación de bajo nivel TCP. |
| **GitKraken** | Gestión visual del control de versiones y ramas. |
| **ObjectSerialization** | Transmisión de objetos complejos por red. |
| **Multi-threading** | Gestión de múltiples clientes concurrentes. |

</div>

---

## 👥 Autores

<div align="center">

  <table>
    <tr>
      <td align="center">
        <a href="https://github.com/EnriqueBDeL">
          <img src="https://github.com/EnriqueBDeL.png" width="100px;" alt="Foto Enrique"/><br>
          <sub><b>EnriqueBDeL</b></sub>
        </a>
      </td>
      <td align="center">
        <a href="https://github.com/Agata-gp">
          <img src="https://github.com/Agata-gp.png" width="100px;" alt="Foto Agata"/><br>
          <sub><b>Agata-gp</b></sub>
        </a>
      </td>
    </tr>
  </table>

  <br>
  <i>[ Desarrollado para la asignatura Desarrollo de Aplicaciones Distribuidas ]</i>
  
</div>
