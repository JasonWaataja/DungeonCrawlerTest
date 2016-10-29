struct Light2D {
	float intensity;
	vec3 color;
	vec2 position;
}; 
out vec4 outColor;
in vec2 position;
uniform Light2D light;
uniform sampler2D texture1;

uniform float num;

void main() {
	vec3 fragColor = vec4(1,1,1,1);
	fragColor = texture2D(texture1, gl_TexCoord[0].st);
	float lightDecrease = distance(position, light.position);
	vec3 lighting = light.intensity * light.color - vec3(lightDecrease,lightDecrease,lightDecrease);
	fragColor.x = max(0, fragColor.x + lighting.x);
	fragColor.y = max(0, fragColor.y + lighting.y);
	fragColor.z = max(0, fragColor.z + lighting.z);
	if (distance(position, light.position) < .01f) {
		fragColor = vec4(0,0,0,0);
	}
    //gl_FragColor = vec4(fragColor,1);
    outColor = vec4(fragColor, 1);
}