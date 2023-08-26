#version 450

layout(location = 0) in vec2 col;
layout(location = 1) in vec3 normal;
layout(location = 2) in vec3 ligVec;
layout(location = 3) in float visibility;

layout(location = 0) out vec4 colO;

uniform sampler2D map;
uniform sampler2D samplerbl;
uniform sampler2D samplerr;
uniform sampler2D samplerg;
uniform sampler2D samplerb;
uniform vec3 ambLig;
uniform vec3 ligCol;
uniform vec3 sky;

void main() {
    vec4 blend = texture(map,col);
    vec2 col2 = col * 16;
    vec4 bl = texture(samplerbl,col2) * 1 - (blend.r + blend.g + blend.b);
    vec4 r = texture(samplerr,col2) * blend.r;
    vec4 g = texture(samplerg,col2) * blend.g;
    vec4 b = texture(samplerb,col2) * blend.b;

    vec3 nNormal = normalize(normal);
    vec3 lNormal = normalize(ligVec);

    float nDot = dot(nNormal,lNormal);
    float f = max(nDot,0.0f);
    vec3 diffuse = f * ligCol;
    diffuse += ambLig;

    vec4 texCol = bl + r + g + b;

    if(texCol.a < 0.5f) discard;

    colO = vec4(diffuse,1.0f) * texCol;
    colO = mix(vec4(sky,1.0f),colO,clamp(visibility,0.0f,1.0f));
}
