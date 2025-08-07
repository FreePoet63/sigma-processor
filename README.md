# SigmaBank Department Processor

Java-консольное приложение для обработки `.sb` файлов сотрудников и менеджеров после неудачной миграции в SigmaBank.

[📄 Техническое задание (PDF)](https://drive.google.com/file/d/1k3jNEquI8y83JFwgW03usYdeZGh3JrM1/view?usp=drivesdk)

---

## 📦 Клонирование проекта

```bash
git clone https://github.com/FreePoet63/sigma-processor.git
cd sigma-processor
```

> Или просто скачай `.zip` архив с репозитория и распакуй.

---

## ✅ Требования

- Java **17+**
- Maven **3.6+**
- JUnit **Jupiter (5.x)** — для тестов (идёт в `pom.xml`)

---

## 📂 Структура проекта

```
project-root/
├── input/              # Входные .sb-файлы (исходные)
├── output/             # Выходные файлы (департаменты, логи, статистика) - создается автоматически
├── src/                # Исходный код на Java
├── pom.xml             # Maven-конфигурация
└── README.md           # Инструкция (этот файл)
```

---

## 🛠️ Сборка

```bash
mvn clean package
```

После сборки будет доступен:

```
target/sigma-processor-1.0.jar
```

---

## ▶️ Примеры запуска

### 1. Сортировка по имени, статистика в консоль

```bash
java -jar target/sigma-processor-1.0.jar --sort=name --order=asc --stat
```

### 2. Сортировка по зарплате, статистика в файл

```bash
java -jar target/sigma-processor-1.0.jar --sort=salary --order=desc --stat --output=file --path=output/statistics.txt
```

### 3. Только статистика без сортировки

```bash
java -jar target/sigma-processor-1.0.jar --stat
```

---

## 📥 Входные данные

Положи `.sb` файлы в папку `input/`, например:

```
Manager,1,Jane Smith,5000,HR
Employee,101,John Doe,3000,1
Employee,102,Bad Guy,abc,1
```

Сейчас в папке `input/`, два файла: teamA.sb и teamB.sb

---

## 📤 Выходные данные

Программа создаёт:

- `output/HR.sb`, `Sales.sb`, ... — по департаментам
- `output/error.log` — с ошибочными строками
- `output/statistics.txt` — при `--output=file`

---

## 🧪 Тестирование

```bash
mvn test
```

✔ Используется JUnit Jupiter (версия 5.x)  
✔ Тесты лежат в `src/test/java/...`

---

## ⚠️ Обработка ошибок

- `--order` без `--sort` → ❌ ошибка
- Неверный `--sort` или `--order` → ❌ ошибка
- `--output=file` без `--path` → ❌ ошибка
- Неизвестные параметры → ❌ ошибка

---

## 👨‍💻 Юрий Разливинский

Проект создан для тестового задания **SHIFT 2025**.
