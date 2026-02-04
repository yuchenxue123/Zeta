#version 330

#moj_import <minecraft:dynamictransforms.glsl>

layout (std140) uniform RectangleUniforms {
    vec4 u_rect;
    vec4 u_radius;

    vec4 u_color_tl;
    vec4 u_color_tr;
    vec4 u_color_bl;
    vec4 u_color_br;

    vec4 u_color_shadow;

    float u_edge_softness;
    float u_shadow_softness;
};

#define u_rect_center   u_rect.xy
#define u_rect_size     u_rect.zw

in vec2 v_position;

out vec4 FragColor;

float rounded_box_sdf(vec2 center_pos, vec2 half_size, vec4 radius) {
    radius.xy = (center_pos.x > 0.0) ? radius.xy : radius.zw;
    radius.x = (center_pos.y > 0.0) ? radius.x : radius.y;

    vec2 q = abs(center_pos) - half_size + radius.x;
    return min(max(q.x, q.y), 0.0) + length(max(q, 0.0)) - radius.x;
}

void main() {
    vec2 half_size = u_rect_size * 0.5;

    float distance = rounded_box_sdf(
        v_position - u_rect_center,
        half_size,
        u_radius
    );

    float alpha_rect = 1.0 - smoothstep(0.0, u_edge_softness, distance);
    float alpha_shadow = 1.0 - smoothstep(-u_shadow_softness, u_shadow_softness, distance);

    vec2 uv = (v_position - (u_rect_center - half_size)) / u_rect_size;
    uv = clamp(uv, 0.0, 1.0);

    vec4 color_top = mix(u_color_tl, u_color_tr, uv.x);
    vec4 color_bottom = mix(u_color_bl, u_color_br, uv.x);
    vec4 color_rect = mix(color_bottom, color_top, uv.y);

    vec4 color_shadow = vec4(
    u_color_shadow.rgb,
    u_color_shadow.a * alpha_shadow
    );

    FragColor = mix(color_shadow, color_rect, alpha_rect);
}