#version 450

layout(location = 0) in vec3 pos;
layout(location = 1) in vec2 tex;

layout(location = 0) out vec2 texO;

uniform mat4 transform;
uniform mat4 projection;
uniform mat4 view;

void main() {
    gl_Position = projection * view * transform * vec4(pos,1.0);
    texO = tex;
}
