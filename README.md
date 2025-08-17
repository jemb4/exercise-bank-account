# 🏦 Cuenta bancaria

[![Java](https://img.shields.io/badge/Java-21-blue?logo=openjdk)](https://www.oracle.com/java/)
[![JUnit](https://img.shields.io/badge/JUnit-5-green?logo=junit5)](https://junit.org/junit5/)
[![Maven](https://img.shields.io/badge/Maven-3.9.6-red?logo=apachemaven)](https://maven.apache.org/)
[![Coverage](https://img.shields.io/badge/Coverage-100%25-brightgreen?logo=codecov)](#)

## 📌 Descripción

Programa en Java que modela el comportamiento de una **cuenta bancaria** con herencia y polimorfismo.  
Incluye clases para cuentas de ahorro y cuentas corrientes, con manejo de saldo, retiros, consignaciones, comisiones y sobregiros.

- ✅ Diseño orientado a objetos con herencia
- ✅ Diagrama UML incluido
- ✅ Pruebas unitarias completas
- ✅ Cobertura de código ≥ 70%

## 🚀 Comenzando

### Prerrequisitos

- JDK 21
- Maven 3.9.6+
- Git

### Instalación

```bash
git clone https://github.com/tu-usuario/tabla-multiplicar.git
cd tabla-multiplicar
mvn clean install
```

## 📝 Requisitos de implementación

- Clase **Cuenta** con:
  - Saldo (`float`)
  - Número de consignaciones (`int`, inicial 0)
  - Número de retiros (`int`, inicial 0)
  - Tasa anual (`float`)
  - Comisión mensual (`float`, inicial 0)
- Métodos:
  - `consignar(float cantidad)`
  - `retirar(float cantidad)`
  - `calcularInteresMensual()`
  - `extractoMensual()`
  - `imprimir()`
- **Cuenta de Ahorros**:
  - Activa/inactiva según saldo mínimo $10,000
  - Límite de 4 retiros sin comisión adicional
- **Cuenta Corriente**:
  - Atributo `sobregiro`
  - Permite retiros superiores al saldo
  - Las consignaciones reducen el sobregiro

## 📊 UML

## 📸 Test Coverage

![Cobertura de tests](./image.png)  
_(captura de VSCode)_
