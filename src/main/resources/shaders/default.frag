#version 450

layout(location = 0) in vec2 col;
layout(location = 1) in vec3 normal;
layout(location = 2) in vec3 ligVec;
layout(location = 3) in float visibility;

layout(location = 0) out vec4 colO;

uniform sampler2D sampler;
uniform vec3 ambLig;
uniform vec3 ligCol;
uniform vec3 sky;

void main() {
    vec3 nNormal = normalize(normal);
    vec3 lNormal = normalize(ligVec);

    float nDot = dot(nNormal,lNormal);
    float f = max(nDot,0.0f);
    vec3 diffuse = f * ligCol;
    diffuse += ambLig;

    vec4 texCol = texture(sampler,col);

    if(texCol.a < 0.5f) discard;

    colO = vec4(diffuse,1.0f) * texCol;
    colO = mix(vec4(sky,1.0f),colO,clamp(visibility,0.0f,1.0f));
}
