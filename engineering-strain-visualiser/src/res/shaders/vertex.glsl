#version 330

layout (location=0) in vec3 positions;
layout (location=1) in vec3 colours;

in vec3 position;

out vec3 colour;

void main() {
	gl_Position = vec4(.175, .3, .3, 1) * vec4(position, 1);
	colour = colours;
}