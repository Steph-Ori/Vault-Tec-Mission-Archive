<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Vault-Tec Mission Archive</title>
    <style>
        body {
            background-color: black;
            color: #00ff66;
            font-family: Consolas, monospace;
            padding: 30px;
        }

        .terminal {
            border: 2px solid #00ff66;
            padding: 20px;
            max-width: 900px;
            margin: auto;
            box-shadow: 0 0 12px #00ff66;
        }

        h1, h2 {
            margin-top: 0;
        }

        .subtext {
            opacity: 0.85;
            margin-bottom: 20px;
        }

        input[type="text"], select {
            width: 70%;
            padding: 8px;
            background: black;
            color: #00ff66;
            border: 1px solid #00ff66;
            font-family: Consolas, monospace;
            margin-bottom: 10px;
        }

        button, a.button-link {
            background: black;
            color: #00ff66;
            border: 1px solid #00ff66;
            padding: 8px 12px;
            text-decoration: none;
            font-family: Consolas, monospace;
            cursor: pointer;
        }

        button:hover, a.button-link:hover {
            background: #00ff66;
            color: black;
        }

        .dashboard {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 12px;
            margin: 20px 0;
        }

        .dashboard-card {
            border: 1px solid #00ff66;
            padding: 12px;
            text-align: center;
            box-shadow: 0 0 6px #00ff66;
        }

        .dashboard-card strong {
            display: block;
            font-size: 20px;
            margin-top: 6px;
        }

        .health-section {
            margin: 30px 0 25px 0;
            clear: both;
        }

        .health-section p {
            margin: 0 0 10px 0;
            opacity: 0.85;
        }

        ul {
            list-style: none;
            padding-left: 0;
        }

        li {
            margin-bottom: 10px;
            padding: 8px;
            border-bottom: 1px solid #00ff66;
        }

        .task-row {
            display: flex;
            justify-content: space-between;
            align-items: center;
            gap: 10px;
        }

        .mission-info {
            line-height: 1.5;
        }

        .mission-actions {
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .empty {
            opacity: 0.8;
        }

        hr {
            border-color: #00ff66;
            margin: 20px 0;
        }
    </style>
</head>
<body>
<div class="terminal">
    <h1>ROBCO INDUSTRIES UNIFIED OS</h1>
    <h2>VAULT-TEC MISSION ARCHIVE SYSTEM</h2>

    <p class="subtext">
        &gt;&gt; Authorized personnel may create, scan, and purge vault mission records.
    </p>

    <div class="dashboard">
        <div class="dashboard-card">
            TOTAL MISSIONS
            <strong>${totalMissions}</strong>
        </div>

        <div class="dashboard-card">
            VAULT EMERGENCIES
            <strong>${criticalMissions}</strong>
        </div>

        <div class="dashboard-card">
            COMPLETED
            <strong>${completedMissions}</strong>
        </div>

        <div class="dashboard-card">
            SYSTEM STATUS
            <strong>ONLINE</strong>
        </div>

        <div class="dashboard-card">
            ACCESS LEVEL
            <strong>OVERSEER</strong>
        </div>

        <div class="dashboard-card">
            TERMINAL MODE
            <strong>ARCHIVE</strong>
        </div>
    </div>

    <div class="health-section">
        <p>&gt;&gt; SYSTEM HEALTH ENDPOINT:</p>
        <a class="button-link" href="health">CHECK VAULT STATUS</a>
    </div>

    <hr>

    <form action="add" method="post">
        <label for="title">ENTER MISSION DIRECTIVE:</label><br><br>
        <input type="text" id="title" name="title" required>

        <br>

        <label for="category">SELECT VAULT DEPARTMENT:</label><br><br>
        <select id="category" name="category">
            <option value="Maintenance">Maintenance</option>
            <option value="Security">Security</option>
            <option value="Research">Research</option>
            <option value="Supply">Supply</option>
            <option value="Emergency">Emergency</option>
        </select>

        <br>

        <label for="priority">ASSIGN PRIORITY LEVEL:</label><br><br>
        <select id="priority" name="priority">
            <option value="Routine">Routine</option>
            <option value="Priority">Priority</option>
            <option value="Urgent">Urgent</option>
            <option value="Vault Emergency">Vault Emergency</option>
        </select>

        <br>

        <button type="submit">STORE MISSION</button>
    </form>

    <br>

    <form action="todos" method="get">
        <label for="search">SCAN MISSION ARCHIVE:</label><br><br>
        <input type="text" id="search" name="search">
        <button type="submit">SCAN ARCHIVE</button>
    </form>

    <c:if test="${not empty searchTerm}">
        <p class="empty">&gt;&gt; ARCHIVE FILTER APPLIED: ${searchTerm}</p>
    </c:if>

    <hr>

    <h2>MISSION ARCHIVE</h2>

    <c:choose>
        <c:when test="${empty todoItems}">
            <p class="empty">&gt;&gt; ARCHIVE EMPTY. NO ACTIVE MISSION RECORDS FOUND.</p>
        </c:when>

        <c:otherwise>
            <ul>
                <c:forEach var="item" items="${todoItems}">
                    <li>
                        <div class="task-row">
                            <span class="mission-info">
                                MISSION-${item.id} :: ${item.title}
                                <br>
                                DEPT: ${item.category} | PRIORITY: ${item.priority} | STATUS: ${item.status}
                            </span>

                            <div class="mission-actions">
                                <form action="updateStatus" method="post" style="display: inline;">
                                    <input type="hidden" name="id" value="${item.id}">

                                    <select name="status" style="width: auto; margin-bottom: 0;">
                                        <option value="Assigned" ${item.status == 'Assigned' ? 'selected' : ''}>Assigned</option>
                                        <option value="In Progress" ${item.status == 'In Progress' ? 'selected' : ''}>In Progress</option>
                                        <option value="Completed" ${item.status == 'Completed' ? 'selected' : ''}>Completed</option>
                                        <option value="Archived" ${item.status == 'Archived' ? 'selected' : ''}>Archived</option>
                                    </select>

                                    <button type="submit">UPDATE</button>
                                </form>

                                <a class="button-link" href="delete?id=${item.id}">PURGE RECORD</a>
                            </div>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>