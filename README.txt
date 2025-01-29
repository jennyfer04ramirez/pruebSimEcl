Voy a dividir la solución en dos partes:  

1. **Backend (Jakarta EE + Jersey + PostgreSQL o datos simulados)**  
2. **Frontend (Angular para consumir el servicio y mostrar los datos en una tabla)**  

También incluiré los pasos para subir el código a **GitHub** y crear la documentación con capturas de pantalla.  

---

# **📌 Backend - Jakarta EE con Jersey y PostgreSQL**  

### **1️⃣ Crear Proyecto Maven en Eclipse**  
- **Nuevo Proyecto → Maven Project**  
- **Arquetipo**: `maven-archetype-webapp`  
- **Nombre**: `simulacionApellido-backend`  

---

### **2️⃣ Configurar `pom.xml`**  
Agrega las dependencias necesarias:  

```xml
<dependencies>
    <!-- Jakarta EE API -->
    <dependency>
        <groupId>jakarta.ws.rs</groupId>
        <artifactId>jakarta.ws.rs-api</artifactId>
        <version>3.0.0</version>
        <scope>provided</scope>
    </dependency>

    <!-- Jersey para implementar JAX-RS -->
    <dependency>
        <groupId>org.glassfish.jersey.containers</groupId>
        <artifactId>jersey-container-servlet</artifactId>
        <version>3.0.2</version>
    </dependency>

    <!-- JSON con Jackson -->
    <dependency>
        <groupId>org.glassfish.jersey.media</groupId>
        <artifactId>jersey-media-json-jackson</artifactId>
        <version>3.0.2</version>
    </dependency>

    <!-- PostgreSQL Driver -->
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <version>42.3.8</version>
    </dependency>
</dependencies>
```

---

### **3️⃣ Configurar `web.xml`**
En `src/main/webapp/WEB-INF/web.xml`:

```xml
<web-app xmlns="http://jakarta.ee/xml/ns/jakartaee"
         version="5.0">
    <servlet>
        <servlet-name>Jersey REST API</servlet-name>
        <servlet-class>org.glassfish.jersey.servlet.ServletContainer</servlet-class>
        <init-param>
            <param-name>jersey.config.server.provider.packages</param-name>
            <param-value>com.simulacionApellido.rest</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>

    <servlet-mapping>
        <servlet-name>Jersey REST API</servlet-name>
        <url-pattern>/api/*</url-pattern>
    </servlet-mapping>
</web-app>
```

---

### **4️⃣ Crear Modelo `Usuario.java`**  
Crea el paquete `com.simulacionApellido.model` y agrega:

```java
package com.simulacionApellido.model;

public class Usuario {
    private String cedula;
    private String nombre;
    private double consumo;
    private double deuda;

    public Usuario(String cedula, String nombre, double consumo, double deuda) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.consumo = consumo;
        this.deuda = deuda;
    }

    public String getCedula() { return cedula; }
    public String getNombre() { return nombre; }
    public double getConsumo() { return consumo; }
    public double getDeuda() { return deuda; }
}
```

---

### **5️⃣ Crear Servicio REST (`UsuarioService.java`)**  
En `com.simulacionApellido.rest`:

```java
package com.simulacionApellido.rest;

import com.simulacionApellido.model.Usuario;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Path("/usuarios")
public class UsuarioService {
    private static Map<String, Usuario> usuarios = new HashMap<>();

    static {
        usuarios.put("1234567890", new Usuario("1234567890", "Juan Pérez", 350.5, 120.75));
        usuarios.put("0987654321", new Usuario("0987654321", "María López", 420.3, 80.20));
    }

    @GET
    @Path("/{cedula}")
    @Produces(MediaType.APPLICATION_JSON)
    public Usuario obtenerUsuario(@PathParam("cedula") String cedula) {
        Usuario usuario = usuarios.get(cedula);
        if (usuario == null) {
            throw new NotFoundException("Usuario no encontrado");
        }
        return usuario;
    }
}
```

📌 **Ejemplo de respuesta JSON:**  

```json
{
    "cedula": "1234567890",
    "nombre": "Juan Pérez",
    "consumo": 350.5,
    "deuda": 120.75
}
```

---

### **6️⃣ Ejecutar en Tomcat o Jetty**
```sh
mvn jetty:run
```
Ahora la API está disponible en:  
➡ `http://localhost:8080/api/usuarios/1234567890`

---

# **📌 Frontend - Angular**  

### **1️⃣ Crear Proyecto Angular**  
```sh
ng new simulacionApellido-frontend
cd simulacionApellido-frontend
```

### **2️⃣ Instalar `HttpClientModule`**  
Abre `app.module.ts` y agrega:

```typescript
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  imports: [HttpClientModule]
})
export class AppModule { }
```

---

### **3️⃣ Crear Servicio Angular**  
```sh
ng generate service services/backend
```

Edita `backend.service.ts`:

```typescript
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BackendService {
  private apiUrl = 'http://localhost:8080/api/usuarios/';

  constructor(private http: HttpClient) {}

  obtenerUsuario(cedula: string): Observable<any> {
    return this.http.get(`${this.apiUrl}${cedula}`);
  }
}
```

---

### **4️⃣ Crear Componente**
Edita `app.component.ts`:

```typescript
import { Component } from '@angular/core';
import { BackendService } from './services/backend.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent {
  cedula = '';
  usuario: any = null;
  error = '';

  constructor(private backendService: BackendService) {}

  buscarUsuario() {
    this.backendService.obtenerUsuario(this.cedula).subscribe({
      next: data => {
        this.usuario = data;
        this.error = '';
      },
      error: err => {
        this.error = 'Usuario no encontrado';
        this.usuario = null;
      }
    });
  }
}
```

---

### **5️⃣ Agregar la Vista (`app.component.html`)**
```html
<div>
  <h1>Consulta de Consumo y Deuda</h1>
  <input [(ngModel)]="cedula" placeholder="Ingrese su cédula">
  <button (click)="buscarUsuario()">Consultar</button>

  <div *ngIf="error">{{ error }}</div>

  <table *ngIf="usuario">
    <tr>
      <th>Nombre</th>
      <th>Consumo (kWh)</th>
      <th>Deuda ($)</th>
    </tr>
    <tr>
      <td>{{ usuario.nombre }}</td>
      <td>{{ usuario.consumo }}</td>
      <td>{{ usuario.deuda }}</td>
    </tr>
  </table>
</div>
```

---

### **6️⃣ Ejecutar Angular**
```sh
ng serve --open
```

---

# **📌 Subir el Código a GitHub**
```sh
git init
git add .
git commit -m "Primera versión"
git branch -M main
git remote add origin https://github.com/tuusuario/simulacionApellido.git
git push -u origin main
```

---

# **📌 Documentación**
1. **Capturar Pantallas**: Backend funcionando (`Postman` o navegador) y Angular mostrando datos.
2. **Crear un documento Word o PDF** con:
   - Descripción del proyecto.
   - Capturas de pantalla.
   - Enlace al repositorio GitHub.

---

### 🎯 **¡Listo! Ahora tienes una aplicación completa con Jakarta EE y Angular!** 🚀
