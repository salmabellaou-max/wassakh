#!/usr/bin/env python3
"""
Simple helper to extract basic structure from Java class files
This will help us understand the code structure to recreate source files
"""
import subprocess
import os
import re
from pathlib import Path

def get_class_info(class_file):
    """Extract basic information from a class file using javap"""
    try:
        # Run javap with private members and verbose output
        result = subprocess.run(
            ['javap', '-p', '-v', class_file],
            capture_output=True,
            text=True,
            timeout=5
        )

        if result.returncode == 0:
            return result.stdout
        return None
    except Exception as e:
        print(f"Error processing {class_file}: {e}")
        return None

def extract_fields_and_methods(javap_output):
    """Extract field and method signatures from javap output"""
    if not javap_output:
        return [], []

    fields = []
    methods = []

    lines = javap_output.split('\n')
    in_fields = False
    in_methods = False

    for line in lines:
        line = line.strip()

        # Look for field declarations
        if 'private' in line or 'public' in line or 'protected' in line:
            if '(' not in line and ';' in line:  # It's a field
                # Extract field info
                match = re.search(r'(private|public|protected)\s+(static\s+)?(final\s+)?(\w+)\s+(\w+);', line)
                if match:
                    fields.append({
                        'modifier': match.group(1),
                        'static': 'static' if match.group(2) else '',
                        'final': 'final' if match.group(3) else '',
                        'type': match.group(4),
                        'name': match.group(5)
                    })
            elif '(' in line:  # It's a method
                # Extract method signature
                methods.append(line)

    return fields, methods

# Process all class files
class_dir = Path('/home/user/wassakh/MyWelly_AuthModule')
class_files = list(class_dir.glob('*.class'))

print(f"Found {len(class_files)} class files")
print("\nKey classes to recreate:")

important_classes = [
    'SignUpWindow',
    'LoginWindow',
    'SearchDoctorsWindow',
    'DashboardWindow',
    'AuthenticationService',
    'SearchService',
    'ProfileService',
    'AppointmentService',
    'DatabaseConnection',
    'UIConstants'
]

for class_name in important_classes:
    class_file = class_dir / f'{class_name}.class'
    if class_file.exists():
        print(f"\n{'='*60}")
        print(f"Class: {class_name}")
        print('='*60)
        info = get_class_info(str(class_file))
        if info:
            # Print first 50 lines to get class structure
            lines = info.split('\n')[:80]
            for line in lines:
                if any(keyword in line for keyword in ['class ', 'interface ', 'extends ', 'implements ', 'private ', 'public ', 'protected ']):
                    print(line)
