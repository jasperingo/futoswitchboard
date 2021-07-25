
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<header class="ff-bold">
    <nav class="navbar navbar-expand-md navbar-light bg-gold py-3">
        <div class="container">
            <a class="navbar-brand" href="<%= request.getContextPath() %>">
                <img src="res/img/logo.jpg" width="50" height="50" alt="The website's and FUTO's logo" class="bg-white d-inline-block align-middle" />
                <h1 class="text-truncate d-none d-sm-inline-block align-middle text-green">FUTOSwitchBoard</h1>
            </a>

            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link text-green" href="#">About us</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-green" href="#">Contact us</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-green" href="login">Log In</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</header>




