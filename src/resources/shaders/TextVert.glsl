#version 150 core

in vec3 in_Position;
in vec2 in_TextureCoords;

out vec2 pass_TextureCoords;

uniform mat4 view;

void main(void)
{
	gl_Position = view * vec4(in_Position, 1.0);
	pass_TextureCoords = in_TextureCoords;
}