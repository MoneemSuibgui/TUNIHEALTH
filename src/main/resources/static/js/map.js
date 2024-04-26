
// Initialize and add the map
function initMap() {
    // The location of the center of the map (for example, your office location)
    var center = {
        lat: 40.7128, // latitude
        lng: -74.0060 // longitude
    };
    // The map, centered at the defined location
    var map = new google.maps.Map(
        document.getElementById('map'), {
            zoom: 10, // adjust zoom level as needed
            center: center
        });
    // The marker, positioned at the defined location
    var marker = new google.maps.Marker({
        position: center,
        map: map
    });
}
