import controller.Sistema;
import models.*;
import exceptions.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Sistema sistema = new Sistema();
        Scanner teclado = new Scanner(System.in);
        boolean salir = false;

        System.out.println("=================================================");
        System.out.println("   SISTEMA DE TURNOS INTERACTIVO - GYM           ");
        System.out.println("=================================================");

        while (!salir) {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Registrar un Socio");
            System.out.println("2. Registrar un Profesor");
            System.out.println("3. Crear y Registrar un Turno");
            System.out.println("4. Inscribir un Socio a un Turno (por DNI)");
            System.out.println("5. Registrar Asistencia a un Turno");
            System.out.println("6. Cancelar Reserva de un Turno");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opcion: ");
            
            String opcion = teclado.nextLine();

            switch (opcion) {
                case "1":
                    System.out.println("\n[REGISTRO DE SOCIO]");
                    System.out.print("Nombre: ");
                    String nomS = teclado.nextLine();
                    System.out.print("Apellido: ");
                    String apeS = teclado.nextLine();
                    System.out.print("DNI: ");
                    String dniS = teclado.nextLine();
                    System.out.print("Telefono: ");
                    String telS = teclado.nextLine();
                    System.out.print("Fecha de Nacimiento (AAAA-MM-DD): ");
                    String fechaNacStr = teclado.nextLine();
                    System.out.print("Telefono de Contacto: ");
                    String telContS = teclado.nextLine();
                    
                    try {
                        LocalDate fn = LocalDate.parse(fechaNacStr);
                        Socio nuevoSocio = new Socio(nomS, apeS, dniS, telS, fn, telContS);
                        sistema.registrarSocio(nuevoSocio);
                        System.out.println("-> Socio registrado con exito localmente.");
                    } catch (DateTimeParseException e) {
                        System.out.println("Error: Formato de fecha invalido. Use AAAA-MM-DD.");
                    }
                    break;

                case "2":
                    System.out.println("\n[REGISTRO DE PROFESOR]");
                    System.out.print("Nombre: ");
                    String nomP = teclado.nextLine();
                    System.out.print("Apellido: ");
                    String apeP = teclado.nextLine();
                    System.out.print("DNI: ");
                    String dniP = teclado.nextLine();
                    System.out.print("Telefono: ");
                    String telP = teclado.nextLine();

                    Profesor nuevoProfe = new Profesor(nomP, apeP, dniP, telP);
                    sistema.registrarProfesor(nuevoProfe);
                    System.out.println("-> Profesor registrado con exito localmente.");
                    break;

                case "3":
                    System.out.println("\n[CREAR NUEVO TURNO]");
                    System.out.print("ID del Turno (Numerico): ");
                    int idT = Integer.parseInt(teclado.nextLine());
                    System.out.print("DNI del Profesor a cargo: ");
                    String dniProfTurno = teclado.nextLine();
                    System.out.print("Horario del Turno (HH:MM): ");
                    String horaStr = teclado.nextLine();
                    System.out.print("Cupo Maximo (Numerico): ");
                    int cupo = Integer.parseInt(teclado.nextLine());

                    // Nota: Para asociar el profesor deberian buscarlo en el sistema.
                    // Creamos una instancia rapida con ese DNI para la prueba del objeto.
                    Profesor profeTurno = new Profesor("Profesor", "Asignado", dniProfTurno, "000");
                    
                    try {
                        LocalTime horario = LocalTime.parse(horaStr);
                        Turno nuevoTurno = new Turno(idT, profeTurno, LocalDate.now().plusDays(1), horario, cupo);
                        sistema.registrarTurno(nuevoTurno);
                        System.out.println("-> Turno ID " + idT + " creado para mañana con cupo de " + cupo + " personas.");
                    } catch (DateTimeParseException e) {
                        System.out.println("Error: Formato de hora invalido. Use HH:MM.");
                    }
                    break;

                case "4":
                    System.out.println("\n[INSCRIBIR SOCIO A TURNO]");
                    System.out.print("Ingrese el DNI del Socio: ");
                    String dniInsc = teclado.nextLine();
                    System.out.print("Ingrese el ID del Turno: ");
                    int idInsc = Integer.parseInt(teclado.nextLine());

                    try {
                        sistema.registrarInscripcion(dniInsc, idInsc);
                        System.out.println("-> Inscripcion exitosa realizada a traves del controlador.");
                    } catch (SocioNoEncontrado e) {
                        System.out.println("Error del Sistema: " + e.getMessage());
                    } catch (CupoLleno e) {
                        System.out.println("Error del Sistema: " + e.getMessage());
                    }
                    break;

                case "5":
                    System.out.println("\n[REGISTRAR ASISTENCIA]");
                    System.out.print("Ingrese DNI del Socio: ");
                    String dniAsis = teclado.nextLine();
                    System.out.print("Ingrese ID del Turno: ");
                    int idAsis = Integer.parseInt(teclado.nextLine());
                    System.out.print("¿Asistio? (S/N): ");
                    boolean asistio = teclado.nextLine().equalsIgnoreCase("S");

                    try {
                        sistema.registrarAsistenciaTurno(dniAsis, idAsis, asistio);
                        System.out.println("-> Asistencia guardada correctamente.");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case "6":
                    System.out.println("\n[CANCELAR RESERVA]");
                    System.out.print("Ingrese DNI del Socio: ");
                    String dniCanc = teclado.nextLine();
                    System.out.print("Ingrese ID del Turno: ");
                    int idCanc = Integer.parseInt(teclado.nextLine());

                    try {
                        sistema.cancelarReservaTurno(dniCanc, idCanc);
                        System.out.println("-> Reserva cancelada. Cupo liberado.");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case "7":
                    System.out.println("\nCerrando el sistema del gimnasio. ¡Hasta luego!");
                    salir = true;
                    break;

                default:
                    System.out.println("Opcion invalida. Intente de nuevo.");
            }
        }
        teclado.close();
    }
}
