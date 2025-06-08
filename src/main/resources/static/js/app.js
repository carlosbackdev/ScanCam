// Variable global para almacenar las capturas
let captures = [];

// Función principal para obtener datos
function fetchCaptures() {
    fetch('/data/obtener')
        .then(response => {
            if (!response.ok) throw new Error('Error al obtener datos');
            return response.json();
        })
        .then(data => {
            const dataList = data || [];

            if (captures.length < dataList.length) {
                captures = dataList;
                renderCaptures(getSelectedLocation());
                loadLocationOptions();
                console.log('Datos cargados correctamente:', captures);
            }

            startRealTimeUpdates();
        })
        .catch(error => {
            console.error('Error:', error);
            document.getElementById('captures-container').innerHTML = `
                <div class="error-message">
                    Error al cargar los datos. Por favor, intente nuevamente.
                </div>
            `;
        });
}

// Función para obtener la ubicación seleccionada actualmente
function getSelectedLocation() {
    const select = document.getElementById('location-select');
    return select ? select.value : 'all';
}

// Función para renderizar las capturas (con filtro opcional)
function renderCaptures(filterLocation = 'all') {
    const container = document.getElementById('captures-container');
    container.innerHTML = '<div class="loader">Cargando datos...</div>';

    let filteredCaptures = filterLocation === 'all'
        ? captures
        : captures.filter(c => c.location === filterLocation);

    updateStatistics(filteredCaptures);

    if (filteredCaptures.length === 0) {
        container.innerHTML = '<div class="no-data">No hay datos disponibles para esta ubicación</div>';
        return;
    }

    container.innerHTML = '';
    filteredCaptures.forEach(capture => {
        const captureElement = document.createElement('div');
        captureElement.className = 'capture-card';

        captureElement.innerHTML = `
            <div class="image-container">
                <img src="${capture.imageUrl}" alt="Captura de ${capture.location}" class="capture-image" 
                     onerror="this.src='fallback-image.jpg';this.onerror=null;">
            </div>
            <div class="capture-info">
                <div class="capture-location">${capture.city}</div>   
                <div class="capture-location">${capture.location}</div>
                <div class="capture-date">${formatDate(capture.date)}</div>
                <div class="capture-people">
                    <span class="people-icon">👥</span>
                    <span class="people-count">${capture.people} personas</span>
                </div>
            </div>
        `;
        container.appendChild(captureElement);
    });
}

// Función para formatear la fecha
function formatDate(dateString) {
    const options = {
        year: 'numeric',
        month: 'short',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    };
    return new Date(dateString).toLocaleString('es-ES', options);
}

// Función para actualizar estadísticas
function updateStatistics(captures) {
    document.getElementById('total-people').textContent =
        captures.reduce((sum, c) => sum + c.people, 0);
    document.getElementById('total-locations').textContent =
        new Set(captures.map(c => c.location)).size;
}

// Función para cargar opciones de ubicación
function loadLocationOptions() {
    const select = document.getElementById('location-select');

    while (select.options.length > 1) {
        select.remove(1);
    }

    const locations = [...new Set(captures.map(c => c.location))].sort();

    locations.forEach(location => {
        const option = document.createElement('option');
        option.value = location;
        option.textContent = location;
        select.appendChild(option);
    });
}

// Función para actualizaciones en tiempo real
function startRealTimeUpdates() {
    setInterval(() => {
        console.log('Actualizando datos en tiempo real...');
        fetchCaptures();
    }, 30000); // cada 30 segundos
}

// Evento para iniciar la captura
document.getElementById('start-btn').addEventListener('click', () => {
    const url = document.getElementById('url-input').value;

    if (!url) {
        alert('Por favor, introduce una URL');
        return;
    }

    fetch('/capture/start', {
        method: 'POST',
        headers: {
            'Content-Type': 'text/plain'
        },
        body: url
    })
        .then(response => response.text())
        .then(data => {
            alert(`Captura iniciada: ${data}`);
        })
        .catch(error => {
            console.error('Error al iniciar captura:', error);
            alert('Error al iniciar captura');
        });
});

// Inicializar todo cuando cargue la página
document.addEventListener('DOMContentLoaded', () => {
    fetchCaptures();

    // Escuchar cambios en el selector de ubicación
    document.getElementById('location-select').addEventListener('change', function () {
        const selectedLocation = this.value;
        renderCaptures(selectedLocation);
    });
});
